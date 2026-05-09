package hotel.ui;

import hotel.exception.InvalidDateException;
import hotel.exception.RoomUnavailableException;
import hotel.model.pessoas.Customer;
import hotel.model.quartos.Room;
import hotel.model.reservas.Reservation;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class ReservationsView {

    private final HotelController controller = HotelController.getInstance();
    private ScrollPane scroll;
    private VBox panel;
    private VBox listContainer;
    private ComboBox<Customer> customerBox;
    private ComboBox<Room> roomBox;

    public ScrollPane getView() {
        if (scroll == null) {
            panel = new VBox(20);
            panel.setPadding(new Insets(30));
            panel.setStyle("-fx-background-color: #1a1a2e;");

            Label title = new Label("Reservation Management");
            title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

            VBox form = createForm();

            Label listTitle = new Label("Registered Reservations");
            listTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

            listContainer = new VBox(8);
            refreshReservationList();

            panel.getChildren().addAll(title, form, listTitle, listContainer);
            scroll = new ScrollPane(panel);
            scroll.setFitToWidth(true);
            scroll.setStyle("-fx-background: #1a1a2e; -fx-background-color: transparent;");
        }
        return scroll;
    }

    public void refreshReservationList() {
        if (listContainer == null) {
            return;
        }
        listContainer.getChildren().clear();
        List<Reservation> reservations = controller.getAllReservations();

        if (reservations.isEmpty()) {
            Label empty = new Label("No reservations registered.");
            empty.setStyle("-fx-text-fill: #aaaaaa;");
            listContainer.getChildren().add(empty);
            return;
        }

        for (Reservation reservation : reservations) {
            HBox row = new HBox(20);
            row.setPadding(new Insets(10, 15, 10, 15));
            row.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8;");

            Label id = new Label("#" + reservation.getId());
            id.setStyle("-fx-text-fill: #e94560; -fx-font-weight: bold; -fx-font-size: 13px;");
            id.setPrefWidth(30);

            Label customer = new Label(" " + reservation.getCustomer().getName());
            customer.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
            customer.setPrefWidth(130);

            Label room = new Label(" Room " + reservation.getRoom().getNumber());
            room.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");
            room.setPrefWidth(100);

            Label dates = new Label(reservation.getCheckInDate() + " -> " + reservation.getCheckOutDate());
            dates.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");
            dates.setPrefWidth(180);

            Label total = new Label(reservation.getTotalPrice() + "€");
            total.setStyle("-fx-text-fill: #00c853; -fx-font-size: 13px; -fx-font-weight: bold;");
            total.setPrefWidth(70);

            Label status = new Label(reservation.getStatus().toString());
            status.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");

            row.getChildren().addAll(id, customer, room, dates, total, status);
            listContainer.getChildren().add(row);
        }
    }

    private VBox createForm() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(15));
        form.setStyle("-fx-background-color: #16213e; -fx-background-radius: 10;");

        Label formTitle = new Label("New Reservation");
        formTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

        customerBox = new ComboBox<>();
        refreshCustomerCombo();
        customerBox.setPromptText("Select Customer");
        customerBox.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });
        customerBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        roomBox = new ComboBox<>();
        refreshRoomCombo();
        roomBox.setPromptText("Select Room");
        roomBox.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : "Room " + item.getNumber() + " - " + item.getRoomType() + " (" + item.getPricePerNight() + "€/night)");
            }
        });
        roomBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : "Room " + item.getNumber() + " - " + item.getRoomType() + " (" + item.getPricePerNight() + "€/night)");
            }
        });

        DatePicker checkInPicker = new DatePicker();
        checkInPicker.setPromptText("Check-in Date");
        checkInPicker.setValue(LocalDate.now());

        DatePicker checkOutPicker = new DatePicker();
        checkOutPicker.setPromptText("Check-out Date");
        checkOutPicker.setValue(LocalDate.now().plusDays(1));

        Label message = new Label("");
        message.setStyle("-fx-font-size: 13px;");

        Button reserveButton = new Button("Make Reservation");
        reserveButton.setStyle("-fx-background-color: #e94560; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8 16; -fx-background-radius: 5; -fx-cursor: hand;");

        reserveButton.setOnAction(e -> {
            try {
                Customer customer = customerBox.getValue();
                Room room = roomBox.getValue();

                if (customer == null || room == null) {
                    message.setStyle("-fx-text-fill: #ff5252;");
                    message.setText(" Please select a customer and a room!");
                    return;
                }

                Reservation reservation = controller.getHotelService().makeReservation(
                        customer, room, checkInPicker.getValue(), checkOutPicker.getValue());

                message.setStyle("-fx-text-fill: #00c853;");
                message.setText(" Reservation #" + reservation.getId() + " created! Total: " + reservation.getTotalPrice() + "€");

                refreshRoomCombo();
                refreshReservationList();
            } catch (RoomUnavailableException | InvalidDateException ex) {
                message.setStyle("-fx-text-fill: #ff5252;");
                message.setText(" " + ex.getMessage());
            }
        });

        HBox row1 = new HBox(10, customerBox, roomBox);
        HBox row2 = new HBox(10, checkInPicker, checkOutPicker);
        form.getChildren().addAll(formTitle, row1, row2, reserveButton, message);
        return form;
    }

    private void refreshCustomerCombo() {
        if (customerBox == null) {
            return;
        }
        customerBox.getItems().clear();
        customerBox.getItems().addAll(controller.getAllCustomers());
    }

    private void refreshRoomCombo() {
        if (roomBox == null) {
            return;
        }
        roomBox.getItems().clear();
        roomBox.getItems().addAll(controller.getAvailableRooms());
    }

    public void refreshCombos() {
        refreshCustomerCombo();
        refreshRoomCombo();
    }
}
