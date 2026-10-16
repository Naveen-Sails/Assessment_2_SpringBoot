package online.store.services;

import online.store.model.Order;

import java.util.List;

public interface OrdersService {
    void placeOrders(Iterable<Order> orders);

    List<Order> findOrdersByEmail(String email);
}
