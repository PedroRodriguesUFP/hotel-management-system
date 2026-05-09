package hotel.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        super("The check-out date cannot be before or equal to the check-in date.");
    }
}
