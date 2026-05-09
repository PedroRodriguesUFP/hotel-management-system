package hotel.model;

import hotel.model.pagamentos.Payment;
import hotel.model.pessoas.Customer;
import hotel.model.pessoas.Employee;
import hotel.model.quartos.Room;
import hotel.model.reservas.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private List<Room> rooms;
    private List<Customer> customers;
    private List<Employee> employees;
    private List<Reservation> reservations;
    private List<Payment> payments;

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    public String getName() { return name; }
    public List<Room> getRooms() { return rooms; }
    public List<Customer> getCustomers() { return customers; }
    public List<Employee> getEmployees() { return employees; }
    public List<Reservation> getReservations() { return reservations; }
    public List<Payment> getPayments() { return payments; }

    public void addRoom(Room room) { rooms.add(room); }
    public void addCustomer(Customer customer) { customers.add(customer); }
    public void addEmployee(Employee employee) { employees.add(employee); }
    public void addReservation(Reservation reservation) { reservations.add(reservation); }
    public void addPayment(Payment payment) { payments.add(payment); }
}
