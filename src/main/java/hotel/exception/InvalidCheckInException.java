package hotel.exception;

public class InvalidCheckInException extends RuntimeException {
    public InvalidCheckInException(int reservationId, String reason) {
        super("Cannot check in reservation #" + reservationId + ": " + reason);
    }
}
