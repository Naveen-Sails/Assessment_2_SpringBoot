package online.store.services.impl;

import online.store.exceptions.InvalidRequestException;
import online.store.exceptions.MissingCreditCardException;
import online.store.model.CheckoutRequest;
import online.store.model.Order;
import online.store.services.CheckoutService;
import online.store.services.CreditCardValidationService;
import online.store.services.OrdersService;
import online.store.services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final OrdersService ordersService;
    private final ProductsService productsService;
    private final CreditCardValidationService creditCardValidationService;

    // Save the checkout as one transaction to avoid partially placed orders.
    @Transactional
    @Override
    public void checkout(CheckoutRequest checkoutRequest) {
        if (checkoutRequest == null) {
            throw new InvalidRequestException("Invalid request");
        }
        if (isNullOrBlank(checkoutRequest.getCreditCard())) {
            throw new MissingCreditCardException("Credit card information is missing");
        }
        if (isNullOrBlank(checkoutRequest.getFirstName())) {
            throw new InvalidRequestException("First name is missing");
        }
        if (isNullOrBlank(checkoutRequest.getLastName())) {
            throw new InvalidRequestException("Last name is missing");
        }
        if (checkoutRequest.getProducts() == null || checkoutRequest.getProducts().isEmpty()) {
            throw new InvalidRequestException("At least one product is required");
        }

        creditCardValidationService.validate(checkoutRequest.getCreditCard());

        // Build the full batch before saving so an invalid item cannot leave a partial checkout.
        List<Order> orders = new ArrayList<>(checkoutRequest.getProducts().size());
        for (CheckoutRequest.ProductInfo productInfo : checkoutRequest.getProducts()) {
            if (productInfo == null) {
                throw new InvalidRequestException("Product information is required");
            }
            if (productInfo.getQuantity() <= 0) {
                throw new InvalidRequestException("Product quantity must be greater than zero");
            }
            orders.add(new Order(
                    null,
                    checkoutRequest.getFirstName(),
                    checkoutRequest.getLastName(),
                    checkoutRequest.getEmail(),
                    checkoutRequest.getShippingAddress(),
                    productInfo.getQuantity(),
                    checkoutRequest.getCreditCard(),
                    productsService.getProductForOrder(productInfo.getProductId())
            ));
        }

        ordersService.placeOrders(orders);
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
