package hotel.model.pessoas;

public class Limpeza extends Funcionario {

    private String andar; // andar responsável

    public Limpeza(String nome, String email, String telefone,
                   String numeroFuncionario, double salario, String andar) {
        super(nome, email, telefone, numeroFuncionario, salario);
        this.andar = andar;
    }

    public String getAndar() { return andar; }
    public void setAndar(String andar) { this.andar = andar; }

    @Override
    public String toString() {
        return super.toString() + " | Andar responsável: " + andar;
    }
}
