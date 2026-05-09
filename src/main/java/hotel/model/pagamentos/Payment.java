package hotel.model.pagamentos;

import hotel.model.reservas.Reservation;

import java.time.LocalDateTime;

public class Payment {

    private static int nextId = 1;

    private int id;
    private Reservation reservation;
    private double amount;
    private PaymentMethod method;
    private LocalDateTime paidAt;

    public Payment(Reservation reservation, PaymentMethod method) {
        this.id = nextId++;
        this.reservation = reservation;
        this.amount = reservation.getTotalPrice();
        this.method = method;
        this.paidAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public Reservation getReservation() { return reservation; }
    public double getAmount() { return amount; }
    public PaymentMethod getMethod() { return method; }
    public LocalDateTime getPaidAt() { return paidAt; }

    @Override
    public String toString() {
        return "Payment #" + id +
                " | Reservation #" + reservation.getId() +
                " | Amount: " + amount + "€" +
                " | Method: " + method +
                " | Date: " + paidAt;
    }
}
