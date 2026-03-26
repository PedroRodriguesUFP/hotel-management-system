package hotel.model.pessoas;

public abstract class Funcionario extends Pessoa {

        private String numeroFuncionario;
        private double salario;

        public Funcionario(String nome, String email, String telefone,
                           String numeroFuncionario, double salario) {
            super(nome, email, telefone);
            this.numeroFuncionario = numeroFuncionario;
            this.salario = salario;
        }

        public String getNumeroFuncionario() { return numeroFuncionario; }
        public double getSalario() { return salario; }

        public void setNumeroFuncionario(String numeroFuncionario) {
            this.numeroFuncionario = numeroFuncionario;
        }
        public void setSalario(double salario) { this.salario = salario; }

        @Override
        public String toString() {
            return super.toString() + " | Nº: " + numeroFuncionario +
                    " | Salário: " + salario + "€";
        }
}