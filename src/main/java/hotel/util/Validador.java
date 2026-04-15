package hotel.util;

import hotel.exception.DataInvalidaException;
import hotel.exception.FormatoInvalidoException;

import java.time.LocalDate;

public class Validador {

    private Validador() {} // Classe utilitária — não instanciável

    public static void validarNif(String nif) throws FormatoInvalidoException {
        if (nif == null || !nif.matches("\\d{9}")) {
            throw new FormatoInvalidoException("NIF", "deve ter exatamente 9 dígitos");
        }
    }

    public static void validarEmail(String email) throws FormatoInvalidoException {
        if (email == null || !email.matches("^[\\w.+\\-]+@[\\w\\-]+\\.[a-zA-Z]{2,}$")) {
            throw new FormatoInvalidoException("Email", "formato inválido (ex: nome@dominio.com)");
        }
    }

    public static void validarTelefone(String telefone) throws FormatoInvalidoException {
        if (telefone == null || !telefone.matches("\\d{9,15}")) {
            throw new FormatoInvalidoException("Telefone", "deve ter entre 9 a 15 dígitos");
        }
    }

    public static void validarNome(String nome) throws FormatoInvalidoException {
        if (nome == null || nome.trim().length() < 2) {
            throw new FormatoInvalidoException("Nome", "deve ter pelo menos 2 caracteres");
        }
    }

    public static void validarDatas(LocalDate entrada, LocalDate saida) throws DataInvalidaException {
        if (entrada == null || saida == null || !entrada.isBefore(saida)) {
            throw new DataInvalidaException();
        }
    }

    public static void validarPrecoPorNoite(double preco) throws FormatoInvalidoException {
        if (preco <= 0) {
            throw new FormatoInvalidoException("Preço por noite", "deve ser maior que 0");
        }
    }
}