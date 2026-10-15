package online.store.services;

import online.store.exceptions.CreditCardValidationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Service
public class CreditCardValidationService {
    private static final String CREDIT_CARD_FORMAT = "^[0-9]{16}$";
    // This local list is for the sample validator, not a production payment system.
    private static final Set<String> STOLEN_CREDIT_CARDS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList("1111111111111111", "9999888877776666"))
    );

    public void validate(String creditCardNumber) {
        validateNumberOfDigits(creditCardNumber);
        validateNotStolenCreditCard(creditCardNumber);
    }

    private void validateNumberOfDigits(String creditCardNumber) {
        if (!creditCardNumber.matches(CREDIT_CARD_FORMAT)) {
            throw new CreditCardValidationException("Credit card is invalid");
        }
    }

    private void validateNotStolenCreditCard(String creditCardNumber) {
        if (STOLEN_CREDIT_CARDS.contains(creditCardNumber)) {
            throw new CreditCardValidationException("Credit card is invalid");
        }
    }
}
