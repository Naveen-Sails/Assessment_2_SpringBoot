package online.store.services;

import online.store.exceptions.InvalidRequestException;
import online.store.model.Order;
import online.store.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrderRepository orderRepository;
    @Value("${products.service.max-number-of-items:25}")
    private final long maxNumberOfItems;

    @Transactional
    public void placeOrders(Iterable<Order> orders) {
        validateNumberOfItemsOrdered(orders);
        orderRepository.saveAll(orders);
    }

    public List<Order> findOrdersByEmail(String email) {
        return orderRepository.findByEmailWithProduct(email);
    }

    private void validateNumberOfItemsOrdered(Iterable<Order> orders) {
        long totalNumberOfItems = 0;
        for (Order order : orders) {
            long quantity = order.getQuantity();
            if (quantity <= 0) {
                throw new InvalidRequestException("Product quantity must be greater than zero");
            }
            // Check before adding so the total cannot exceed the configured limit.
            if (quantity > maxNumberOfItems - totalNumberOfItems) {
                throw new InvalidRequestException(String.format("Number of products %d exceeded the limit of %d",
                        totalNumberOfItems + quantity, maxNumberOfItems));
            }
            totalNumberOfItems += quantity;
        }
    }
}
