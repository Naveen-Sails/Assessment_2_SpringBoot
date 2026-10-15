package online.store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Keep card-validation details out of the response.
    @ExceptionHandler(CreditCardValidationException.class)
    public ResponseEntity<String> handleCreditCardValidation(CreditCardValidationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Credit card is invalid, please use another form of payment");
    }

    @ExceptionHandler(MissingCreditCardException.class)
    public ResponseEntity<String> handleMissingCreditCard(MissingCreditCardException exception) {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequest(InvalidRequestException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<String> handleMalformedRequest(Exception exception) {
        return ResponseEntity.badRequest().body("Invalid request");
    }

    // Return a stable message instead of exposing unexpected exception details.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred");
    }
}
