package hotel.ui;

import hotel.exception.CustomerAlreadyExistsException;
import hotel.exception.InvalidFormatException;
import hotel.model.pessoas.Customer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class CustomersView {

    private final HotelController controller = HotelController.getInstance();
    private VBox panel;
    private VBox listContainer;

    public VBox getView() {
        if (panel == null) {
            panel = new VBox(20);
            panel.setPadding(new Insets(30));
            panel.setStyle("-fx-background-color: #1a1a2e;");

            Label title = new Label("Customer Management");
            title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

            VBox form = createForm();

            Label listTitle = new Label("Registered Customers");
            listTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

            listContainer = new VBox(8);
            refreshCustomerList();

            panel.getChildren().addAll(title, form, listTitle, listContainer);
        }
        return panel;
    }

    public void refreshCustomerList() {
        listContainer.getChildren().clear();
        List<Customer> customers = controller.getAllCustomers();

        if (customers.isEmpty()) {
            Label empty = new Label("No customers registered.");
            empty.setStyle("-fx-text-fill: #aaaaaa;");
            listContainer.getChildren().add(empty);
            return;
        }

        for (Customer customer : customers) {
            HBox row = new HBox(20);
            row.setPadding(new Insets(10, 15, 10, 15));
            row.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8;");

            Label name = new Label(" " + customer.getName());
            name.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold;");
            name.setPrefWidth(150);

            Label email = new Label(" " + customer.getEmail());
            email.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");
            email.setPrefWidth(180);

            Label taxId = new Label(" " + customer.getTaxId());
            taxId.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");

            row.getChildren().addAll(name, email, taxId);
            listContainer.getChildren().add(row);
        }
    }

    private VBox createForm() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(15));
        form.setStyle("-fx-background-color: #16213e; -fx-background-radius: 10;");

        Label formTitle = new Label("Register New Customer");
        formTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

        TextField nameField = createField("Full name");
        TextField emailField = createField("Email");
        TextField phoneField = createField("Phone");
        TextField taxIdField = createField("Tax ID");
        TextField nationalityField = createField("Nationality");

        Label message = new Label("");
        message.setStyle("-fx-font-size: 13px;");

        Button registerButton = new Button("Register Customer");
        registerButton.setStyle("-fx-background-color: #e94560; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8 16; -fx-background-radius: 5; -fx-cursor: hand;");

        registerButton.setOnAction(e -> {
            try {
                Customer customer = new Customer(
                        nameField.getText(),
                        emailField.getText(),
                        phoneField.getText(),
                        taxIdField.getText(),
                        nationalityField.getText()
                );

                controller.getHotelService().registerCustomer(customer);
                message.setStyle("-fx-text-fill: #00c853; -fx-font-size: 13px;");
                message.setText(" Customer registered successfully!");

                nameField.clear();
                emailField.clear();
                phoneField.clear();
                taxIdField.clear();
                nationalityField.clear();
                refreshCustomerList();
            } catch (CustomerAlreadyExistsException | InvalidFormatException ex) {
                message.setStyle("-fx-text-fill: #ff5252; -fx-font-size: 13px;");
                message.setText(" " + ex.getMessage());
            }
        });

        HBox row1 = new HBox(10, nameField, emailField, phoneField);
        HBox row2 = new HBox(10, taxIdField, nationalityField);
        form.getChildren().addAll(formTitle, row1, row2, registerButton, message);
        return form;
    }

    private TextField createField(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setPrefWidth(180);
        field.setStyle("-fx-background-color: #0f3460; -fx-text-fill: white; -fx-prompt-text-fill: #aaaaaa; -fx-padding: 8; -fx-background-radius: 5;");
        return field;
    }
}
