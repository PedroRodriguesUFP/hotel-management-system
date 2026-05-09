package hotel.exception;

public class InvalidFormatException extends RuntimeException {
    public InvalidFormatException(String field, String reason) {
        super("Invalid field [" + field + "]: " + reason);
    }
}
