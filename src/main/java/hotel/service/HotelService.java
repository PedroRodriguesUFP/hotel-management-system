package hotel.service;

import hotel.exception.QuartoIndisponivelException;
import hotel.model.Hotel;
import hotel.model.pessoas.Cliente;
import hotel.model.quartos.Quarto;
import hotel.model.reservas.EstadoReserva;
import hotel.model.reservas.Reserva;

import java.time.LocalDate;

public class HotelService {

    private Hotel hotel;

    public HotelService(Hotel hotel) {
        this.hotel = hotel;
    }

    // Regra de Negócio: Fazer Reserva
    public Reserva fazerReserva(Cliente cliente, Quarto quarto, LocalDate entrada, LocalDate saida) throws QuartoIndisponivelException {
        if (!quarto.isDisponivel()) {
            throw new QuartoIndisponivelException("Erro: O quarto " + quarto.getNumero() + " já se encontra ocupado!");
        }

        Reserva novaReserva = new Reserva(cliente, quarto, entrada, saida);
        novaReserva.setEstado(EstadoReserva.CONFIRMADA);

        // Bloqueia o quarto
        quarto.setDisponivel(false);

        hotel.adicionarReserva(novaReserva);
        return novaReserva;
    }

    // Regra de Negócio: Cancelar Reserva
    public void cancelarReserva(int idReserva) {
        for (Reserva r : hotel.getReservas()) {
            if (r.getId() == idReserva && r.getEstado() != EstadoReserva.CANCELADA) {
                r.setEstado(EstadoReserva.CANCELADA);
                r.getQuarto().setDisponivel(true); // Liberta o quarto novamente
                System.out.println("-> Reserva #" + idReserva + " cancelada com sucesso. Quarto libertado.");
                return;
            }
        }
        System.out.println("-> Aviso: Reserva #" + idReserva + " não encontrada ou já se encontra cancelada.");
    }

    // Funcionalidade: Listar apenas quartos disponíveis
    public void listarQuartosDisponiveis() {
        System.out.println("\n--- Quartos Disponíveis ---");
        boolean encontrou = false;
        for (Quarto q : hotel.getQuartos()) {
            if (q.isDisponivel()) {
                System.out.println(q.toString());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("De momento, não há quartos disponíveis.");
        }
    }

    // Funcionalidade: Listar todas as reservas
    public void listarReservas() {
        System.out.println("\n--- Registo de Reservas ---");
        for (Reserva r : hotel.getReservas()) {
            System.out.println(r.toString());
        }
    }
}