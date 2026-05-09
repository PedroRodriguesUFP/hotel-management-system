package hotel.service;

import hotel.exception.*;
import hotel.model.Hotel;
import hotel.model.pagamentos.Payment;
import hotel.model.pagamentos.PaymentMethod;
import hotel.model.pessoas.Customer;
import hotel.model.quartos.Room;
import hotel.model.reservas.Reservation;
import hotel.model.reservas.ReservationStatus;
import hotel.util.Validator;

import java.time.LocalDate;

public class HotelService {

    private final Hotel hotel;

    public HotelService(Hotel hotel) {
        this.hotel = hotel;
    }

    public void registerCustomer(Customer customer)
            throws CustomerAlreadyExistsException, InvalidFormatException {

        Validator.validateName(customer.getName());
        Validator.validateEmail(customer.getEmail());
        Validator.validatePhone(customer.getPhone());
        Validator.validateTaxId(customer.getTaxId());

        for (Customer existing : hotel.getCustomers()) {
            if (existing.getTaxId().equals(customer.getTaxId())) {
                throw new CustomerAlreadyExistsException(customer.getTaxId());
            }
        }

        hotel.addCustomer(customer);
        System.out.println("-> Customer " + customer.getName() + " registered successfully.");
    }

    public Reservation makeReservation(Customer customer, Room room,
                                       LocalDate checkInDate, LocalDate checkOutDate)
            throws RoomUnavailableException, InvalidDateException {

        Validator.validateDates(checkInDate, checkOutDate);

        if (!room.isAvailable()) {
            throw new RoomUnavailableException(room.getNumber());
        }

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        room.setAvailable(false);
        hotel.addReservation(reservation);
        return reservation;
    }

    public void cancelReservation(int reservationId)
            throws ReservationNotFoundException, ReservationAlreadyCancelledException {

        Reservation reservation = findReservation(reservationId);

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationAlreadyCancelledException(reservationId);
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.getRoom().setAvailable(true);
        System.out.println("-> Reservation #" + reservationId + " cancelled successfully. Room released.");
    }

    public void addRoom(Room room) throws InvalidFormatException {
        Validator.validatePricePerNight(room.getPricePerNight());
        hotel.addRoom(room);
        System.out.println("-> Room " + room.getNumber() + " added successfully.");
    }

    public void listAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        boolean found = false;
        for (Room room : hotel.getRooms()) {
            if (room.isAvailable()) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("There are no available rooms at the moment.");
        }
    }

    public void listReservations() {
        System.out.println("\n--- Reservation Log ---");
        if (hotel.getReservations().isEmpty()) {
            System.out.println("No reservations have been registered.");
            return;
        }
        for (Reservation reservation : hotel.getReservations()) {
            System.out.println(reservation);
        }
    }

    public void checkIn(int reservationId)
            throws ReservationNotFoundException, InvalidCheckInException {

        Reservation reservation = findReservation(reservationId);

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new InvalidCheckInException(reservationId,
                    "the reservation must be CONFIRMED (current status: " + reservation.getStatus() + ")");
        }
        if (LocalDate.now().isBefore(reservation.getCheckInDate())) {
            throw new InvalidCheckInException(reservationId,
                    "the check-in date has not been reached yet (" + reservation.getCheckInDate() + ")");
        }

        reservation.setStatus(ReservationStatus.CHECKED_IN);
        System.out.println("-> Check-in completed successfully for reservation #" + reservationId);
    }

    public void checkOut(int reservationId)
            throws ReservationNotFoundException, InvalidCheckOutException {

        Reservation reservation = findReservation(reservationId);

        if (reservation.getStatus() != ReservationStatus.CHECKED_IN) {
            throw new InvalidCheckOutException(reservationId,
                    "the guest must be CHECKED_IN (current status: " + reservation.getStatus() + ")");
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        reservation.getRoom().setAvailable(true);
        System.out.println("-> Check-out completed successfully for reservation #" + reservationId
                + " | Total: " + reservation.getTotalPrice() + "€");
    }

    public Payment processPayment(int reservationId, PaymentMethod method)
            throws ReservationNotFoundException, ReservationNotCompletedException, PaymentAlreadyProcessedException {

        Reservation reservation = findReservation(reservationId);

        if (reservation.getStatus() != ReservationStatus.COMPLETED) {
            throw new ReservationNotCompletedException(reservationId);
        }

        boolean alreadyPaid = hotel.getPayments().stream()
                .anyMatch(payment -> payment.getReservation().getId() == reservationId);
        if (alreadyPaid) {
            throw new PaymentAlreadyProcessedException(reservationId);
        }

        Payment payment = new Payment(reservation, method);
        hotel.addPayment(payment);
        System.out.println("-> Payment processed successfully! " + payment);
        return payment;
    }

    private Reservation findReservation(int reservationId) throws ReservationNotFoundException {
        return hotel.getReservations().stream()
                .filter(reservation -> reservation.getId() == reservationId)
                .findFirst()
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }
}
