package hotel.exception;

public class ReservationNotCompletedException extends RuntimeException {
    public ReservationNotCompletedException(int reservationId) {
        super("Reservation #" + reservationId + " has not been completed yet (check-out not performed).");
    }
}
