package hotel.ui;

import hotel.exception.InvalidCheckInException;
import hotel.exception.InvalidCheckOutException;
import hotel.exception.PaymentAlreadyProcessedException;
import hotel.exception.ReservationNotCompletedException;
import hotel.exception.ReservationNotFoundException;
import hotel.model.pagamentos.PaymentMethod;
import hotel.model.reservas.Reservation;
import hotel.model.reservas.ReservationStatus;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class CheckInOutView {

    private final HotelController controller = HotelController.getInstance();
    private VBox panel;
    private VBox listContainer;

    public VBox getView() {
        if (panel == null) {
            panel = new VBox(20);
            panel.setPadding(new Insets(30));
            panel.setStyle("-fx-background-color: #1a1a2e;");

            Label title = new Label("Check-in / Check-out");
            title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

            listContainer = new VBox(10);
            refreshReservationList();

            panel.getChildren().addAll(title, listContainer);
        }
        return panel;
    }

    public void refreshReservationList() {
        if (listContainer == null) {
            return;
        }
        listContainer.getChildren().clear();

        List<Reservation> reservations = controller.getAllReservations();
        if (reservations.isEmpty()) {
            Label empty = new Label("No reservations found.");
            empty.setStyle("-fx-text-fill: #aaaaaa;");
            listContainer.getChildren().add(empty);
            return;
        }

        for (Reservation reservation : reservations) {
            listContainer.getChildren().add(createReservationCard(reservation));
        }
    }

    private VBox createReservationCard(Reservation reservation) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #16213e; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #0f3460; -fx-border-width: 1;");

        Label info = new Label("Reservation #" + reservation.getId()
                + " - " + reservation.getCustomer().getName()
                + " | Room " + reservation.getRoom().getNumber()
                + " | " + reservation.getCheckInDate() + " -> " + reservation.getCheckOutDate()
                + " | Total: " + reservation.getTotalPrice() + "€");
        info.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

        Label status = new Label("Status: " + reservation.getStatus());
        status.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + statusColor(reservation.getStatus()) + ";");

        Label message = new Label("");
        message.setStyle("-fx-font-size: 13px;");

        HBox buttons = new HBox(10);

        Button checkInButton = new Button(" Check-in");
        checkInButton.setStyle(buttonStyle("#0f3460"));
        checkInButton.setDisable(reservation.getStatus() != ReservationStatus.CONFIRMED);

        Button checkOutButton = new Button(" Check-out");
        checkOutButton.setStyle(buttonStyle("#0f3460"));
        checkOutButton.setDisable(reservation.getStatus() != ReservationStatus.CHECKED_IN);

        ComboBox<PaymentMethod> paymentBox = new ComboBox<>();
        paymentBox.getItems().addAll(PaymentMethod.values());
        paymentBox.setPromptText("Payment method");
        paymentBox.setVisible(reservation.getStatus() == ReservationStatus.CHECKED_IN);
        paymentBox.setStyle("-fx-background-color: #0f3460; -fx-text-fill: white;");

        checkInButton.setOnAction(e -> {
            try {
                controller.getHotelService().checkIn(reservation.getId());
                status.setText("Status: " + reservation.getStatus());
                status.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + statusColor(reservation.getStatus()) + ";");
                message.setStyle("-fx-text-fill: #00c853;");
                message.setText(" Check-in completed successfully!");
                checkInButton.setDisable(true);
                checkOutButton.setDisable(false);
                paymentBox.setVisible(true);
            } catch (ReservationNotFoundException | InvalidCheckInException ex) {
                message.setStyle("-fx-text-fill: #ff5252;");
                message.setText(" " + ex.getMessage());
            }
        });

        checkOutButton.setOnAction(e -> {
            try {
                if (paymentBox.getValue() == null) {
                    message.setStyle("-fx-text-fill: #ff5252;");
                    message.setText(" Please select a payment method!");
                    return;
                }

                controller.getHotelService().checkOut(reservation.getId());
                controller.getHotelService().processPayment(reservation.getId(), paymentBox.getValue());

                status.setText("Status: " + reservation.getStatus());
                status.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + statusColor(reservation.getStatus()) + ";");
                message.setStyle("-fx-text-fill: #00c853;");
                message.setText(" Check-out and payment completed! Total: " + reservation.getTotalPrice() + "€");
                checkOutButton.setDisable(true);
                paymentBox.setVisible(false);
            } catch (ReservationNotFoundException | InvalidCheckOutException
                     | ReservationNotCompletedException | PaymentAlreadyProcessedException ex) {
                message.setStyle("-fx-text-fill: #ff5252;");
                message.setText(" " + ex.getMessage());
            }
        });

        buttons.getChildren().addAll(checkInButton, checkOutButton, paymentBox);
        card.getChildren().addAll(info, status, buttons, message);
        return card;
    }

    private String statusColor(ReservationStatus status) {
        return switch (status) {
            case CONFIRMED -> "#00c853";
            case CHECKED_IN -> "#e94560";
            case COMPLETED -> "#aaaaaa";
            case CANCELLED -> "#ff5252";
            default -> "white";
        };
    }

    private String buttonStyle(String color) {
        return "-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13px;" +
                "-fx-padding: 8 16;" +
                "-fx-background-radius: 5;" +
                "-fx-cursor: hand;";
    }
}
