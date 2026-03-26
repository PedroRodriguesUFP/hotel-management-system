package hotel.model.quartos;

public class QuartoDouble extends Quarto {

    private boolean temVista;

    public QuartoDouble(int numero, int andar, double precoPorNoite,
                        boolean temVista) {
        super(numero, andar, precoPorNoite);
        this.temVista = temVista;
    }

    public boolean isTemVista() { return temVista; }
    public void setTemVista(boolean temVista) { this.temVista = temVista; }

    @Override
    public String getTipoQuarto() { return "Double"; }

    @Override
    public String toString() {
        return super.toString() + " | Vista: " + (temVista ? "Sim" : "Não");
    }
}