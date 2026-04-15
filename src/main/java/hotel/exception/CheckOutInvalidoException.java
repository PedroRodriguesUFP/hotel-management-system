package hotel.exception;

public class CheckOutInvalidoException extends RuntimeException {
    public CheckOutInvalidoException(int idReserva, String motivo) {
        super("Não é possível fazer check-out na reserva #" + idReserva + ": " + motivo);
    }
}