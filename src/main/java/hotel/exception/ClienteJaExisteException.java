package hotel.exception;

public class ClienteJaExisteException extends RuntimeException {
    public ClienteJaExisteException(String nif) {
        super("Já existe um cliente registado com o NIF: " + nif);
    }
}