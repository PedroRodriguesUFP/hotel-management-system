package hotel.model.quartos;

public class QuartoSimples extends Quarto {

    private boolean temVaranda;

    public QuartoSimples(int numero, int andar, double precoPorNoite,
                         boolean temVaranda) {
        super(numero, andar, precoPorNoite);
        this.temVaranda = temVaranda;
    }

    public boolean isTemVaranda() { return temVaranda; }
    public void setTemVaranda(boolean temVaranda) {
        this.temVaranda = temVaranda;
    }

    @Override
    public String getTipoQuarto() { return "Simples"; }

    @Override
    public String toString() {
        return super.toString() + " | Varanda: " + (temVaranda ? "Sim" : "Não");
    }
}