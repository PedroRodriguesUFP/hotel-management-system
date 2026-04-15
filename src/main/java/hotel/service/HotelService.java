package hotel.service;

import hotel.exception.*;
import hotel.model.Hotel;
import hotel.model.pagamentos.MetodoPagamento;
import hotel.model.pagamentos.Pagamento;
import hotel.model.pessoas.Cliente;
import hotel.model.quartos.Quarto;
import hotel.model.reservas.EstadoReserva;
import hotel.model.reservas.Reserva;
import hotel.util.Validador;

import java.time.LocalDate;

public class HotelService {

    private Hotel hotel;

    public HotelService(Hotel hotel) {
        this.hotel = hotel;
    }

    // ── CLIENTES ──────────────────────────────────────────────────────────────

    public void registarCliente(Cliente cliente)
            throws ClienteJaExisteException, FormatoInvalidoException {

        Validador.validarNome(cliente.getNome());
        Validador.validarEmail(cliente.getEmail());
        Validador.validarTelefone(cliente.getTelefone());
        Validador.validarNif(cliente.getNif());

        for (Cliente c : hotel.getClientes()) {
            if (c.getNif().equals(cliente.getNif())) {
                throw new ClienteJaExisteException(cliente.getNif());
            }
        }
        hotel.adicionarCliente(cliente);
        System.out.println("-> Cliente " + cliente.getNome() + " registado com sucesso.");
    }

    // ── RESERVAS ──────────────────────────────────────────────────────────────

    public Reserva fazerReserva(Cliente cliente, Quarto quarto,
                                LocalDate entrada, LocalDate saida)
            throws QuartoIndisponivelException, DataInvalidaException {

        Validador.validarDatas(entrada, saida);

        if (!quarto.isDisponivel()) {
            throw new QuartoIndisponivelException(quarto.getNumero());
        }

        Reserva novaReserva = new Reserva(cliente, quarto, entrada, saida);
        novaReserva.setEstado(EstadoReserva.CONFIRMADA);
        quarto.setDisponivel(false);

        hotel.adicionarReserva(novaReserva);
        return novaReserva;
    }

    public void cancelarReserva(int idReserva) throws ReservaNaoEncontradaException {
        Reserva reserva = encontrarReserva(idReserva);

        if (reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new ReservaNaoEncontradaException(idReserva);
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        reserva.getQuarto().setDisponivel(true);
        System.out.println("-> Reserva #" + idReserva + " cancelada com sucesso. Quarto libertado.");
    }

    // ── QUARTOS ───────────────────────────────────────────────────────────────

    public void adicionarQuarto(Quarto quarto) throws FormatoInvalidoException {
        Validador.validarPrecoPorNoite(quarto.getPrecoPorNoite());
        hotel.adicionarQuarto(quarto);
        System.out.println("-> Quarto " + quarto.getNumero() + " adicionado com sucesso.");
    }

    // ── LISTAGENS ─────────────────────────────────────────────────────────────

    public void listarQuartosDisponiveis() {
        System.out.println("\n--- Quartos Disponíveis ---");
        boolean encontrou = false;
        for (Quarto q : hotel.getQuartos()) {
            if (q.isDisponivel()) {
                System.out.println(q);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("De momento, não há quartos disponíveis.");
        }
    }

    public void listarReservas() {
        System.out.println("\n--- Registo de Reservas ---");
        if (hotel.getReservas().isEmpty()) {
            System.out.println("Não existem reservas registadas.");
            return;
        }
        for (Reserva r : hotel.getReservas()) {
            System.out.println(r);
        }
    }
    // ── CHECK-IN / CHECK-OUT ──────────────────────────────────────────────────

    public void fazerCheckIn(int idReserva)
            throws ReservaNaoEncontradaException, CheckInInvalidoException {

        Reserva reserva = encontrarReserva(idReserva);

        if (reserva.getEstado() != EstadoReserva.CONFIRMADA) {
            throw new CheckInInvalidoException(idReserva,
                    "a reserva tem de estar CONFIRMADA (estado atual: " + reserva.getEstado() + ")");
        }
        if (LocalDate.now().isBefore(reserva.getDataEntrada())) {
            throw new CheckInInvalidoException(idReserva,
                    "ainda não chegou a data de entrada (" + reserva.getDataEntrada() + ")");
        }

        reserva.setEstado(EstadoReserva.CHECKIN);
        System.out.println("-> Check-in efetuado com sucesso na reserva #" + idReserva);
    }

    public void fazerCheckOut(int idReserva)
            throws ReservaNaoEncontradaException, CheckOutInvalidoException {

        Reserva reserva = encontrarReserva(idReserva);

        if (reserva.getEstado() != EstadoReserva.CHECKIN) {
            throw new CheckOutInvalidoException(idReserva,
                    "o hóspede tem de estar em CHECKIN (estado atual: " + reserva.getEstado() + ")");
        }

        reserva.setEstado(EstadoReserva.CONCLUIDA);
        reserva.getQuarto().setDisponivel(true);
        System.out.println("-> Check-out efetuado com sucesso na reserva #" + idReserva
                + " | Total: " + reserva.getPrecoTotal() + "€");
    }

// ── MÉTODO AUXILIAR PRIVADO ───────────────────────────────────────────────

    private Reserva encontrarReserva(int idReserva) throws ReservaNaoEncontradaException {
        return hotel.getReservas().stream()
                .filter(r -> r.getId() == idReserva)
                .findFirst()
                .orElseThrow(() -> new ReservaNaoEncontradaException(idReserva));
    }

    public Pagamento efetuarPagamento(int idReserva, MetodoPagamento metodo)
            throws ReservaNaoEncontradaException, ReservaNaoConcluidaException, PagamentoJaEfetuadoException {

        Reserva reserva = encontrarReserva(idReserva);

        if (reserva.getEstado() != EstadoReserva.CONCLUIDA) {
            throw new ReservaNaoConcluidaException(idReserva);
        }

        boolean jaFoiPago = hotel.getPagamentos().stream()
                .anyMatch(p -> p.getReserva().getId() == idReserva);
        if (jaFoiPago) {
            throw new PagamentoJaEfetuadoException(idReserva);
        }

        Pagamento pagamento = new Pagamento(reserva, metodo);
        hotel.adicionarPagamento(pagamento);
        System.out.println("-> Pagamento efetuado com sucesso! " + pagamento);
        return pagamento;
    }
}