package hotel;

import hotel.exception.QuartoIndisponivelException;
import hotel.model.Hotel;
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

        // 3. Criar Clientes
        Cliente cli1 = new Cliente("João Silva", "joao@email.com", "912345678", "123456789", "Portuguesa");
        Cliente cli2 = new Cliente("Maria Santos", "maria@email.com", "987654321", "987654321", "Portuguesa");
        meuHotel.adicionarCliente(cli1);
        meuHotel.adicionarCliente(cli2);

        // 4. Testar o Sistema
        service.listarQuartosDisponiveis();

        System.out.println("\n--- A realizar reservas ---");
        try {
            // Reserva com sucesso
            service.fazerReserva(cli1, q102, LocalDate.now(), LocalDate.now().plusDays(3));
            System.out.println("Reserva 1 efetuada para: " + cli1.getNome());

            // Tentar reservar o mesmo quarto para forçar a Exceção
            service.fazerReserva(cli2, q102, LocalDate.now().plusDays(1), LocalDate.now().plusDays(4));

        } catch (QuartoIndisponivelException e) {
            // Aqui capturamos a nossa exceção personalizada!
            System.out.println(e.getMessage());
        }

        // Ver como ficaram os quartos após as reservas
        service.listarQuartosDisponiveis();
        service.listarReservas();

        System.out.println("\n--- A cancelar reserva ---");
        // O id da primeira reserva será 1 (devido ao contadorId estático na classe Reserva)
        service.cancelarReserva(1);

        // Ver o estado final
        service.listarQuartosDisponiveis();
        service.listarReservas();
    }
}