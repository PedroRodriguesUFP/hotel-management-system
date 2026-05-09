package hotel.exception;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String taxId) {
        super("A customer is already registered with Tax ID: " + taxId);
    }
}
