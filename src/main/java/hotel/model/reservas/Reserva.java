package hotel.model.reservas;

import hotel.model.pessoas.Cliente;
import hotel.model.quartos.Quarto;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {

    private static int contadorId = 1;

    private int id;
    private Cliente cliente;
    private Quarto quarto;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private EstadoReserva estado;

    public Reserva(Cliente cliente, Quarto quarto,
                   LocalDate dataEntrada, LocalDate dataSaida) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.estado = EstadoReserva.PENDENTE;
    }

    public long getNumeroNoites() {
        return ChronoUnit.DAYS.between(dataEntrada, dataSaida);
    }

    public double getPrecoTotal() {
        return getNumeroNoites() * quarto.getPrecoPorNoite();
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Quarto getQuarto() { return quarto; }
    public LocalDate getDataEntrada() { return dataEntrada; }
    public LocalDate getDataSaida() { return dataSaida; }
    public EstadoReserva getEstado() { return estado; }

    public void setEstado(EstadoReserva estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Reserva #" + id +
                " | Cliente: " + cliente.getNome() +
                " | Quarto: " + quarto.getNumero() +
                " | Entrada: " + dataEntrada +
                " | Saída: " + dataSaida +
                " | Noites: " + getNumeroNoites() +
                " | Total: " + getPrecoTotal() + "€" +
                " | Estado: " + estado;
    }
}