package hotel.exception;

public class ReservaNaoEncontradaException extends RuntimeException {
    public ReservaNaoEncontradaException(int idReserva) {
        super("A reserva #" + idReserva + " não foi encontrada.");
    }
}