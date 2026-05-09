package hotel.model.reservas;

import hotel.model.pessoas.Customer;
import hotel.model.quartos.Room;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {

    private static int nextId = 1;

    private int id;
    private Customer customer;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;

    public Reservation(Customer customer, Room room,
                       LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = nextId++;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = ReservationStatus.PENDING;
    }

    public long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    public double getTotalPrice() {
        return getNumberOfNights() * room.getPricePerNight();
    }

    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public ReservationStatus getStatus() { return status; }

    public void setStatus(ReservationStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Reservation #" + id +
                " | Customer: " + customer.getName() +
                " | Room: " + room.getNumber() +
                " | Check-in: " + checkInDate +
                " | Check-out: " + checkOutDate +
                " | Nights: " + getNumberOfNights() +
                " | Total: " + getTotalPrice() + "€" +
                " | Status: " + status;
    }
}
