package hotel.exception;

public class ReservationAlreadyCancelledException extends RuntimeException {
    public ReservationAlreadyCancelledException(int reservationId) {
        super("Reservation #" + reservationId + " has already been cancelled.");
    }
}
