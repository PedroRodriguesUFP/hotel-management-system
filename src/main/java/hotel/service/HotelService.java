package hotel.service;

import hotel.exception.ClienteJaExisteException;
import hotel.exception.DataInvalidaException;
import hotel.exception.FormatoInvalidoException;
import hotel.exception.QuartoIndisponivelException;
import hotel.exception.ReservaNaoEncontradaException;
import hotel.model.Hotel;
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

    public void cancelarReserva(int idReserva)
            throws ReservaNaoEncontradaException {

        for (Reserva r : hotel.getReservas()) {
            if (r.getId() == idReserva) {
                if (r.getEstado() == EstadoReserva.CANCELADA) {
                    throw new ReservaNaoEncontradaException(idReserva);
                }
                r.setEstado(EstadoReserva.CANCELADA);
                r.getQuarto().setDisponivel(true);
                System.out.println("-> Reserva #" + idReserva + " cancelada com sucesso. Quarto libertado.");
                return;
            }
        }
        throw new ReservaNaoEncontradaException(idReserva);
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
}