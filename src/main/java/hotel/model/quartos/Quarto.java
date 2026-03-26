package hotel.model.quartos;

public abstract class Quarto {

    private int numero;
    private int andar;
    private double precoPorNoite;
    private boolean disponivel;

    public Quarto(int numero, int andar, double precoPorNoite) {
        this.numero = numero;
        this.andar = andar;
        this.precoPorNoite = precoPorNoite;
        this.disponivel = true; // por defeito está disponível
    }

    public int getNumero() { return numero; }
    public int getAndar() { return andar; }
    public double getPrecoPorNoite() { return precoPorNoite; }
    public boolean isDisponivel() { return disponivel; }

    public void setPrecoPorNoite(double precoPorNoite) {
        this.precoPorNoite = precoPorNoite;
    }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public abstract String getTipoQuarto();

    @Override
    public String toString() {
        return "Quarto " + numero + " | Andar: " + andar +
                " | Tipo: " + getTipoQuarto() +
                " | Preço/Noite: " + precoPorNoite + "€" +
                " | Disponível: " + (disponivel ? "Sim" : "Não");
    }
}