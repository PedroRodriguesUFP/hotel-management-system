package hotel.ui;

import hotel.model.Hotel;
import hotel.model.quartos.*;
import hotel.model.pessoas.Cliente;
import hotel.model.reservas.Reserva;
import hotel.service.HotelService;

import java.util.List;

public class HotelController {

    private static HotelController instancia;
    private HotelService hotelService;
    private Hotel hotel;

    private HotelController() {
        this.hotel = new Hotel("Hotel Central");
        this.hotelService = new HotelService(hotel);
        inicializarDadosTeste();
    }

    public static HotelController getInstancia() {
        if (instancia == null) {
            instancia = new HotelController();
        }
        return instancia;
    }

    private void inicializarDadosTeste() {
        try {
            // Quartos
            hotelService.adicionarQuarto(new QuartoSimples(101, 1, 50.0, false));
            hotelService.adicionarQuarto(new QuartoDouble(102, 1, 80.0, true));
            hotelService.adicionarQuarto(new Suite(201, 2, 200.0, 2, true));

            // Clientes
            hotelService.registarCliente(new Cliente("Joao Silva",
                    "joao@email.com", "910000001", "123456789", "Portuguesa"));
            hotelService.registarCliente(new Cliente("Maria Santos",
                    "maria@email.com", "910000002", "987654321", "Portuguesa"));

        } catch (Exception e) {
            System.out.println("Erro ao inicializar dados: " + e.getMessage());
        }
    }

    public List<Quarto> getTodosQuartos() {
        return hotel.getQuartos();
    }

    public List<Quarto> getQuartosDisponiveis() {
        return hotel.getQuartos().stream()
                .filter(Quarto::isDisponivel)
                .toList();
    }

    public List<Cliente> getTodosClientes() {
        return hotel.getClientes();
    }

    public List<Reserva> getTodasReservas() {
        return hotel.getReservas();
    }

    public HotelService getHotelService() {
        return hotelService;
    }

    public Hotel getHotel() {
        return hotel;
    }
}