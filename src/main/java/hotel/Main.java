package hotel;

import hotel.exception.*;
import hotel.model.Hotel;
import hotel.model.pagamentos.MetodoPagamento;
import hotel.model.pessoas.Cliente;
import hotel.model.quartos.QuartoDouble;
import hotel.model.quartos.QuartoSimples;
import hotel.model.quartos.Suite;
import hotel.service.HotelService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Iniciar Hotel Management System ===\n");

        // 1. Inicializar Hotel e Service
        Hotel meuHotel = new Hotel("Grande Hotel OOP");
        HotelService service = new HotelService(meuHotel);

        // 2. Criar e adicionar Quartos
        QuartoSimples q101 = new QuartoSimples(101, 1, 50.0, false);
        QuartoDouble q102 = new QuartoDouble(102, 1, 80.0, true);
        Suite s201 = new Suite(201, 2, 200.0, 2, true);

        meuHotel.adicionarQuarto(q101);
        meuHotel.adicionarQuarto(q102);
        meuHotel.adicionarQuarto(s201);

        // 3. Registar Clientes — testa ClienteJaExisteException
        System.out.println("--- A registar clientes ---");
        Cliente cli1 = new Cliente("João Silva", "joao@email.com", "912345678", "123456789", "Portuguesa");
        Cliente cli2 = new Cliente("Maria Santos", "maria@email.com", "987654321", "987654321", "Portuguesa");
        Cliente cli3Duplicado = new Cliente("João Duplicado", "joao2@email.com", "911111111", "123456789", "Portuguesa");

        try {
            service.registarCliente(cli1);
            service.registarCliente(cli2);
            service.registarCliente(cli3Duplicado); // NIF duplicado — deve lançar exceção
        } catch (ClienteJaExisteException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        // 4. Listar quartos disponíveis
        service.listarQuartosDisponiveis();

        // 5. Testar fazerReserva — testa DataInvalidaException
        System.out.println("\n--- A testar data inválida ---");
        try {
            service.fazerReserva(cli1, q101, LocalDate.now().plusDays(3), LocalDate.now()); // saída antes da entrada
        } catch (DataInvalidaException e) {
            System.out.println("ERRO: " + e.getMessage());
        } catch (QuartoIndisponivelException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        // 6. Testar fazerReserva — testa QuartoIndisponivelException
        System.out.println("\n--- A realizar reservas ---");
        try {
            service.fazerReserva(cli1, q102, LocalDate.now(), LocalDate.now().plusDays(3));
            System.out.println("-> Reserva 1 efetuada para: " + cli1.getNome());

            service.fazerReserva(cli2, q102, LocalDate.now().plusDays(1), LocalDate.now().plusDays(4)); // quarto ocupado
        } catch (QuartoIndisponivelException e) {
            System.out.println("ERRO: " + e.getMessage());
        } catch (DataInvalidaException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        // 7. Ver estado dos quartos e reservas
        service.listarQuartosDisponiveis();
        service.listarReservas();

        // 8. Testar cancelarReserva — testa ReservaNaoEncontradaException
        System.out.println("\n--- A cancelar reservas ---");
        try {
            service.cancelarReserva(1); // deve funcionar
            service.cancelarReserva(1); // já cancelada — deve lançar exceção
        } catch (ReservaNaoEncontradaException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        try {
            service.cancelarReserva(99); // id inexistente — deve lançar exceção
        } catch (ReservaNaoEncontradaException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        // 9. Estado final
        service.listarQuartosDisponiveis();
        service.listarReservas();
        System.out.println("\n--- A testar Check-in / Check-out ---");
        try {
            service.fazerCheckIn(1);
            service.fazerCheckOut(1);
        } catch (CheckInInvalidoException | CheckOutInvalidoException | ReservaNaoEncontradaException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        // Faz uma reserva nova para testar check-in/check-out
        System.out.println("\n--- A testar Check-in / Check-out ---");
        try {
            service.fazerReserva(cli1, q101, LocalDate.now(), LocalDate.now().plusDays(2));
            // Esta reserva terá id=2 (o contador continua do anterior)
            service.fazerCheckIn(2);
            service.fazerCheckOut(2);
        } catch (CheckInInvalidoException | CheckOutInvalidoException |
                 ReservaNaoEncontradaException | QuartoIndisponivelException |
                 DataInvalidaException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        System.out.println("\n--- A testar Pagamentos ---");
        try {
            service.efetuarPagamento(2, MetodoPagamento.CARTAO);
            service.efetuarPagamento(2, MetodoPagamento.NUMERARIO); // duplicado — deve falhar
        } catch (PagamentoJaEfetuadoException | ReservaNaoConcluidaException | ReservaNaoEncontradaException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

}