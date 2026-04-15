package hotel.exception;

public class FormatoInvalidoException extends RuntimeException {
    public FormatoInvalidoException(String campo, String motivo) {
        super("Campo inválido [" + campo + "]: " + motivo);
    }
}