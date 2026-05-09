package hotel.ui;

import hotel.model.Hotel;
import hotel.model.pessoas.Customer;
import hotel.model.quartos.DoubleRoom;
import hotel.model.quartos.Room;
import hotel.model.quartos.SingleRoom;
import hotel.model.quartos.Suite;
import hotel.model.reservas.Reservation;
import hotel.service.HotelService;

import java.util.List;

public class HotelController {

    private static class Holder {
        private static final HotelController INSTANCE = new HotelController();
    }

    private final HotelService hotelService;
    private final Hotel hotel;

    private HotelController() {
        this.hotel = new Hotel("Hotel Central");
        this.hotelService = new HotelService(hotel);
        initializeSampleData();
    }

    public static HotelController getInstance() {
        return Holder.INSTANCE;
    }

    private void initializeSampleData() {
        try {
            hotelService.addRoom(new SingleRoom(101, 1, 50.0, false));
            hotelService.addRoom(new DoubleRoom(102, 1, 80.0, true));
            hotelService.addRoom(new Suite(201, 2, 200.0, 2, true));

            hotelService.registerCustomer(new Customer("John Silva",
                    "john@email.com", "910000001", "123456789", "Portuguese"));
            hotelService.registerCustomer(new Customer("Mary Santos",
                    "mary@email.com", "910000002", "987654321", "Portuguese"));
        } catch (Exception e) {
            System.out.println("Error while initializing sample data: " + e.getMessage());
        }
    }

    public List<Room> getAllRooms() {
        return hotel.getRooms();
    }

    public List<Room> getAvailableRooms() {
        return hotel.getRooms().stream()
                .filter(Room::isAvailable)
                .toList();
    }

    public List<Customer> getAllCustomers() {
        return hotel.getCustomers();
    }

    public List<Reservation> getAllReservations() {
        return hotel.getReservations();
    }

    public HotelService getHotelService() {
        return hotelService;
    }

    public Hotel getHotel() {
        return hotel;
    }
}
