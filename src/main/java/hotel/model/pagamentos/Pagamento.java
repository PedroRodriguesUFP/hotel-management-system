package hotel.model.pagamentos;

import hotel.model.reservas.Reserva;
import java.time.LocalDateTime;

public class Pagamento {

    private static int contadorId = 1;

    private int id;
    private Reserva reserva;
    private double valor;
    private MetodoPagamento metodo;
    private LocalDateTime dataPagamento;

    public Pagamento(Reserva reserva, MetodoPagamento metodo) {
        this.id = contadorId++;
        this.reserva = reserva;
        this.valor = reserva.getPrecoTotal();
        this.metodo = metodo;
        this.dataPagamento = LocalDateTime.now();
    }

    public int getId() { return id; }
    public Reserva getReserva() { return reserva; }
    public double getValor() { return valor; }
    public MetodoPagamento getMetodo() { return metodo; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }

    @Override
    public String toString() {
        return "Pagamento #" + id +
                " | Reserva #" + reserva.getId() +
                " | Valor: " + valor + "€" +
                " | Método: " + metodo +
                " | Data: " + dataPagamento;
    }
}