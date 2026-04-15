package hotel.exception;

public class PagamentoJaEfetuadoException extends RuntimeException {
    public PagamentoJaEfetuadoException(int idReserva) {
        super("A reserva #" + idReserva + " já tem pagamento efetuado.");
    }
}