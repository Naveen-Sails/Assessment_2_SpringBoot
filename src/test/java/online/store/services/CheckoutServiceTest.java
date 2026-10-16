package online.store.services;

import online.store.exceptions.InvalidRequestException;
import online.store.exceptions.MissingCreditCardException;
import online.store.model.CheckoutRequest;
import online.store.model.Order;
import online.store.model.Product;
import online.store.services.impl.CheckoutServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutServiceTest {
    @Mock
    private OrdersService ordersService;

    @Mock
    private ProductsService productsService;

    @Mock
    private CreditCardValidationService creditCardValidationService;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @Test
    public void checkoutPersistsAllProductsInOneBatch() {
        when(productsService.getProductForOrder(anyLong()))
                .thenReturn(new Product(null, "Sample", "Description", "sample.jpg", 1f, "test"));
        CheckoutRequest request = createRequest(
                "Jane",
                "Doe",
                "1234567890123456",
                Arrays.asList(
                        new CheckoutRequest.ProductInfo(1L, 1L),
                        new CheckoutRequest.ProductInfo(2L, 2L)
                )
        );

        checkoutService.checkout(request);

        verify(creditCardValidationService).validate("1234567890123456");
        verify(productsService).getProductForOrder(1L);
        verify(productsService).getProductForOrder(2L);
        verify(ordersService, times(1)).placeOrders(
                org.mockito.ArgumentMatchers.<Iterable<Order>>argThat(orders -> {
                    long totalQuantity = 0;
                    for (Order order : orders) {
                        totalQuantity += order.getQuantity();
                    }
                    return totalQuantity == 3;
                })
        );
    }

    @Test
    public void checkoutRejectsMissingCreditCardBeforeOtherWork() {
        CheckoutRequest request = createRequest(
                "Jane",
                "Doe",
                " ",
                Arrays.asList(new CheckoutRequest.ProductInfo(1L, 1L))
        );

        assertThrows(MissingCreditCardException.class, () -> checkoutService.checkout(request));

        verifyNoInteractions(ordersService, productsService, creditCardValidationService);
    }

    @Test
    public void checkoutRejectsMissingFirstName() {
        CheckoutRequest request = createRequest(
                " ",
                "Doe",
                "1234567890123456",
                Arrays.asList(new CheckoutRequest.ProductInfo(1L, 1L))
        );

        assertThrows(InvalidRequestException.class, () -> checkoutService.checkout(request));

        verifyNoInteractions(ordersService, productsService, creditCardValidationService);
    }

    @Test
    public void checkoutRejectsNonPositiveProductQuantity() {
        CheckoutRequest request = createRequest(
                "Jane",
                "Doe",
                "1234567890123456",
                Arrays.asList(new CheckoutRequest.ProductInfo(1L, 0L))
        );

        assertThrows(InvalidRequestException.class, () -> checkoutService.checkout(request));

        verify(creditCardValidationService).validate("1234567890123456");
        verifyNoInteractions(ordersService, productsService);
    }

    private CheckoutRequest createRequest(
            String firstName,
            String lastName,
            String creditCard,
            List<CheckoutRequest.ProductInfo> products
    ) {
        return new CheckoutRequest(
                firstName,
                lastName,
                "jane@example.com",
                "1 Main Street",
                products,
                creditCard
        );
    }
}
