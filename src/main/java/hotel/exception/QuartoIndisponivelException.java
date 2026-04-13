package hotel.exception;

public class QuartoIndisponivelException extends RuntimeException {
    public QuartoIndisponivelException(int numeroQuarto) {
        super("O quarto " + numeroQuarto + " não está disponível.");
    }
}