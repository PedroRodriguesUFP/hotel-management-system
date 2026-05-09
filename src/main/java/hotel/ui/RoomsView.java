package hotel.ui;

import hotel.exception.InvalidFormatException;
import hotel.model.quartos.DoubleRoom;
import hotel.model.quartos.Room;
import hotel.model.quartos.SingleRoom;
import hotel.model.quartos.Suite;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class RoomsView {

    private final HotelController controller = HotelController.getInstance();
    private ScrollPane scroll;
    private FlowPane grid;

    public ScrollPane getView() {
        if (scroll == null) {
            VBox panel = new VBox(20);
            panel.setPadding(new Insets(30));
            panel.setStyle("-fx-background-color: #1a1a2e;");

            Label title = new Label("Rooms Management");
            title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

            Label subtitle = new Label("List of all hotel rooms");
            subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

            VBox form = createForm();

            grid = new FlowPane();
            grid.setHgap(15);
            grid.setVgap(15);
            grid.setPadding(new Insets(10, 0, 0, 0));

            refreshGrid();

            panel.getChildren().addAll(title, subtitle, form, grid);
            scroll = new ScrollPane(panel);
            scroll.setFitToWidth(true);
            scroll.setStyle("-fx-background: #1a1a2e; -fx-background-color: transparent;");
        }
        return scroll;
    }

    public void refreshGrid() {
        if (grid == null) {
            return;
        }
        grid.getChildren().clear();
        List<Room> rooms = controller.getAllRooms();
        for (Room room : rooms) {
            grid.getChildren().add(createRoomCard(room));
        }
    }

    private VBox createForm() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(15));
        form.setStyle("-fx-background-color: #16213e; -fx-background-radius: 10;");

        Label formTitle = new Label("Add New Room");
        formTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox fields = new HBox(10);

        TextField numberField = new TextField();
        numberField.setPromptText("No.");
        numberField.setPrefWidth(60);

        TextField floorField = new TextField();
        floorField.setPromptText("Floor");
        floorField.setPrefWidth(60);

        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        priceField.setPrefWidth(70);

        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Single", "Double", "Suite");
        typeBox.setValue("Single");

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #e94560; -fx-text-fill: white; -fx-cursor: hand;");

        Label message = new Label("");
        message.setStyle("-fx-font-size: 13px;");

        addButton.setOnAction(e -> {
            try {
                int number = Integer.parseInt(numberField.getText());
                int floor = Integer.parseInt(floorField.getText());
                double price = Double.parseDouble(priceField.getText());
                String type = typeBox.getValue();

                Room room = switch (type) {
                    case "Double" -> new DoubleRoom(number, floor, price, true);
                    case "Suite" -> new Suite(number, floor, price, 2, true);
                    default -> new SingleRoom(number, floor, price, false);
                };

                controller.getHotelService().addRoom(room);
                message.setStyle("-fx-text-fill: #00c853;");
                message.setText(" Room " + number + " (" + type + ") added!");

                numberField.clear();
                floorField.clear();
                priceField.clear();
                refreshGrid();
            } catch (NumberFormatException ex) {
                message.setStyle("-fx-text-fill: #ff5252;");
                message.setText(" Use numbers only (e.g. 50.5 for price).");
            } catch (InvalidFormatException ex) {
                message.setStyle("-fx-text-fill: #ff5252;");
                message.setText(" " + ex.getMessage());
            } catch (Exception ex) {
                message.setStyle("-fx-text-fill: #ff5252;");
                message.setText(" Unknown error.");
            }
        });

        fields.getChildren().addAll(numberField, floorField, priceField, typeBox, addButton);
        form.getChildren().addAll(formTitle, fields, message);
        return form;
    }

    private VBox createRoomCard(Room room) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setPrefWidth(200);
        card.setStyle("-fx-background-color: #16213e; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #0f3460; -fx-border-width: 1;");

        Label number = new Label("Room " + room.getNumber());
        number.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label type = new Label(" " + room.getRoomType());
        type.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        Label floor = new Label(" Floor " + room.getFloor());
        floor.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        Label price = new Label(" " + room.getPricePerNight() + "€ / night");
        price.setStyle("-fx-font-size: 13px; -fx-text-fill: #e94560;");

        boolean available = room.isAvailable();
        Label status = new Label(available ? " Available" : " Occupied");
        status.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + (available ? "#00c853" : "#ff5252") + ";");

        card.getChildren().addAll(number, type, floor, price, status);
        return card;
    }
}
