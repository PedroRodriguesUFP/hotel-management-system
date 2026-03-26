package hotel.model.pessoas;

public class Rececionista extends Funcionario {

    private String turno;

    public Rececionista(String nome, String email, String telefone,
                        String numeroFuncionario, double salario, String turno) {
        super(nome, email, telefone, numeroFuncionario, salario);
        this.turno = turno;
    }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    @Override
    public String toString() {
        return super.toString() + " | Turno: " + turno;
    }
}