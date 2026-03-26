package hotel.model.pessoas;

public class Cliente extends Pessoa {

    private String nif;
    private String nacionalidade;

    public Cliente(String nome, String email, String telefone,
                   String nif, String nacionalidade) {
        super(nome, email, telefone);
        this.nif = nif;
        this.nacionalidade = nacionalidade;
    }

    public String getNif() { return nif; }
    public String getNacionalidade() { return nacionalidade; }

    public void setNif(String nif) { this.nif = nif; }
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    @Override
    public String toString() {
        return super.toString() + " | NIF: " + nif +
                " | Nacionalidade: " + nacionalidade;
    }
}