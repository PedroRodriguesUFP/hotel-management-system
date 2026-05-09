package hotel.exception;

public class PaymentAlreadyProcessedException extends RuntimeException {
    public PaymentAlreadyProcessedException(int reservationId) {
        super("Reservation #" + reservationId + " has already been paid.");
    }
}
