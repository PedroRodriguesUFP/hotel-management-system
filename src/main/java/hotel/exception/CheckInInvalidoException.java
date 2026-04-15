package hotel.exception;

public class CheckInInvalidoException extends RuntimeException {
    public CheckInInvalidoException(int idReserva, String motivo) {
        super("Não é possível fazer check-in na reserva #" + idReserva + ": " + motivo);
    }
}