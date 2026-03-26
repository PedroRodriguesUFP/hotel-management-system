package hotel.model.quartos;

public class Suite extends Quarto {

    private int numeroQuartos;
    private boolean temJacuzzi;

    public Suite(int numero, int andar, double precoPorNoite,
                 int numeroQuartos, boolean temJacuzzi) {
        super(numero, andar, precoPorNoite);
        this.numeroQuartos = numeroQuartos;
        this.temJacuzzi = temJacuzzi;
    }

    public int getNumeroQuartos() { return numeroQuartos; }
    public boolean isTemJacuzzi() { return temJacuzzi; }

    @Override
    public String getTipoQuarto() { return "Suite"; }

    @Override
    public String toString() {
        return super.toString() + " | Quartos: " + numeroQuartos +
                " | Jacuzzi: " + (temJacuzzi ? "Sim" : "Não");
    }
}
