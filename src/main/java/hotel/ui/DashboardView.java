package hotel.ui;

import hotel.model.quartos.Room;
import hotel.model.reservas.Reservation;
import hotel.model.reservas.ReservationStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class DashboardView {

    private final HotelController controller = HotelController.getInstance();

    public VBox getView() {
        VBox panel = new VBox(25);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: #1a1a2e;");

        Label title = new Label("Dashboard");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitle = new Label("Welcome to Hotel Management System - " + LocalDate.now());
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        HBox cards = createStatsCards();

        Label recentTitle = new Label("Recent Reservations");
        recentTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox recentReservations = createRecentReservations();

        panel.getChildren().addAll(title, subtitle, cards, recentTitle, recentReservations);
        return panel;
    }

    private HBox createStatsCards() {
        HBox cards = new HBox(15);

        List<Room> rooms = controller.getAllRooms();
        List<Reservation> reservations = controller.getAllReservations();

        long available = rooms.stream().filter(Room::isAvailable).count();
        long occupied = rooms.stream().filter(room -> !room.isAvailable()).count();
        long confirmed = reservations.stream().filter(r -> r.getStatus() == ReservationStatus.CONFIRMED).count();
        long totalCustomers = controller.getAllCustomers().size();

        cards.getChildren().addAll(
                createStatCard(" Total Rooms", String.valueOf(rooms.size()), "#0f3460"),
                createStatCard(" Available", String.valueOf(available), "#00c853"),
                createStatCard(" Occupied", String.valueOf(occupied), "#e94560"),
                createStatCard(" Active Reservations", String.valueOf(confirmed), "#f5a623"),
                createStatCard(" Customers", String.valueOf(totalCustomers), "#7b68ee")
        );

        return cards;
    }

    private VBox createStatCard(String title, String value, String color) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setPrefWidth(160);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #16213e; -fx-background-radius: 10; -fx-border-color: " + color + "; -fx-border-width: 2; -fx-border-radius: 10;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #aaaaaa;");
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);

        card.getChildren().addAll(valueLabel, titleLabel);
        return card;
    }

    private VBox createRecentReservations() {
        VBox list = new VBox(8);
        List<Reservation> reservations = controller.getAllReservations();

        if (reservations.isEmpty()) {
            Label empty = new Label("No reservations registered yet.");
            empty.setStyle("-fx-text-fill: #aaaaaa;");
            list.getChildren().add(empty);
            return list;
        }

        List<Reservation> recent = reservations.subList(Math.max(0, reservations.size() - 5), reservations.size());

        for (Reservation reservation : recent) {
            HBox row = new HBox(20);
            row.setPadding(new Insets(10, 15, 10, 15));
            row.setAlignment(Pos.CENTER_LEFT);
            row.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8;");

            Label id = new Label("#" + reservation.getId());
            id.setStyle("-fx-text-fill: #e94560; -fx-font-weight: bold;");
            id.setPrefWidth(40);

            Label customer = new Label(" Customer: " + reservation.getCustomer().getName());
            customer.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
            customer.setPrefWidth(170);

            Label room = new Label(" Room " + reservation.getRoom().getNumber());
            room.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");
            room.setPrefWidth(110);

            Label total = new Label(reservation.getTotalPrice() + "€");
            total.setStyle("-fx-text-fill: #00c853; -fx-font-weight: bold;");
            total.setPrefWidth(80);

            String color = switch (reservation.getStatus()) {
                case CONFIRMED -> "#00c853";
                case CHECKED_IN -> "#e94560";
                case COMPLETED -> "#aaaaaa";
                case CANCELLED -> "#ff5252";
                default -> "white";
            };

            Label status = new Label(reservation.getStatus().toString());
            status.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 13px;");

            row.getChildren().addAll(id, customer, room, total, status);
            list.getChildren().add(row);
        }

        return list;
    }
}
