package hotel.exception;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException() {
        super("A data de saída não pode ser anterior ou igual à data de entrada.");
    }
}