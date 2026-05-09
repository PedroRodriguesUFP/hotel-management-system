package hotel.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(int reservationId) {
        super("Reservation #" + reservationId + " was not found.");
    }
}
