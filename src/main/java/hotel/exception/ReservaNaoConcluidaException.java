package hotel.exception;

public class ReservaNaoConcluidaException extends RuntimeException {
    public ReservaNaoConcluidaException(int idReserva) {
        super("A reserva #" + idReserva + " ainda não foi concluída (check-out não efetuado).");
    }
}