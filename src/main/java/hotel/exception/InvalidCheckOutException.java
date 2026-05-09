package hotel.exception;

public class InvalidCheckOutException extends RuntimeException {
    public InvalidCheckOutException(int reservationId, String reason) {
        super("Cannot check out reservation #" + reservationId + ": " + reason);
    }
}
