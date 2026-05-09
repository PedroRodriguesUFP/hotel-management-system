package hotel.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private BorderPane root;
    private CustomersView customersView;
    private ReservationsView reservationsView;
    private RoomsView roomsView;
    private CheckInOutView checkInOutView;

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.setTop(createHeader());
        root.setLeft(createSidebar());
        showDashboard();

        customersView = new CustomersView();
        reservationsView = new ReservationsView();
        roomsView = new RoomsView();
        checkInOutView = new CheckInOutView();

        Scene scene = new Scene(root, 1000, 650);
        stage.setTitle("Hotel Management System");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: #16213e;");

        Label title = new Label("Hotel Management System");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        header.getChildren().add(title);
        return header;
    }

    private VBox createSidebar() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20, 10, 20, 10));
        menu.setStyle("-fx-background-color: #0f3460;");
        menu.setPrefWidth(180);

        String[] options = {" Dashboard", " Rooms", " Customers", " Reservations", " Check-in/out"};

        for (String option : options) {
            Button button = new Button(option);
            button.setPrefWidth(160);
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER-LEFT; -fx-padding: 10px; -fx-cursor: hand;");
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #e94560; -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER-LEFT; -fx-padding: 10px; -fx-cursor: hand;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER-LEFT; -fx-padding: 10px; -fx-cursor: hand;"));
            button.setOnAction(e -> handleNavigation(option));
            menu.getChildren().add(button);
        }

        return menu;
    }

    private void handleNavigation(String option) {
        switch (option) {
            case " Dashboard" -> showDashboard();
            case " Rooms" -> showRooms();
            case " Customers" -> showCustomers();
            case " Reservations" -> showReservations();
            case " Check-in/out" -> showCheckIn();
        }
    }

    private void showDashboard() {
        root.setCenter(new DashboardView().getView());
    }

    private void showRooms() {
        roomsView.refreshGrid();
        root.setCenter(roomsView.getView());
    }

    private void showCustomers() {
        customersView.refreshCustomerList();
        root.setCenter(customersView.getView());
    }

    private void showReservations() {
        reservationsView.refreshReservationList();
        reservationsView.refreshCombos();
        root.setCenter(reservationsView.getView());
    }

    private void showCheckIn() {
        checkInOutView.refreshReservationList();
        root.setCenter(checkInOutView.getView());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
