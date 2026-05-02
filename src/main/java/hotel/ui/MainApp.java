package hotel.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    private BorderPane root;

    @Override
    public void start(Stage stage) {
        root = new BorderPane();

        // Topo
        HBox topo = criarTopo();
        root.setTop(topo);

        // Menu lateral
        VBox menuLateral = criarMenuLateral();
        root.setLeft(menuLateral);

        // Painel inicial
        mostrarDashboard();

        Scene scene = new Scene(root, 1000, 650);
        stage.setTitle("Hotel Management System");
        stage.setScene(scene);
        stage.show();
    }

    private HBox criarTopo() {
        HBox topo = new HBox();
        topo.setPadding(new Insets(15, 20, 15, 20));
        topo.setStyle("-fx-background-color: #16213e;");

        Label titulo = new Label("Hotel Management System");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        topo.getChildren().add(titulo);
        return topo;
    }

    private VBox criarMenuLateral() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20, 10, 20, 10));
        menu.setStyle("-fx-background-color: #0f3460;");
        menu.setPrefWidth(180);

        String[] opcoes = {" Dashboard", " Quartos", " Clientes",
                " Reservas", " Check-in/out"};

        for (String opcao : opcoes) {
            Button btn = new Button(opcao);
            btn.setPrefWidth(160);
            btn.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-alignment: CENTER-LEFT;
                -fx-padding: 10px;
                -fx-cursor: hand;
            """);

            btn.setOnMouseEntered(e -> btn.setStyle("""
                -fx-background-color: #e94560;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-alignment: CENTER-LEFT;
                -fx-padding: 10px;
                -fx-cursor: hand;
            """));

            btn.setOnMouseExited(e -> btn.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-alignment: CENTER-LEFT;
                -fx-padding: 10px;
                -fx-cursor: hand;
            """));

            btn.setOnAction(e -> tratarNavegacao(opcao));
            menu.getChildren().add(btn);
        }

        return menu;
    }

    private void tratarNavegacao(String opcao) {
        switch (opcao) {
            case " Dashboard" -> mostrarDashboard();
            case " Quartos" -> mostrarQuartos();
            case " Clientes" -> mostrarClientes();
            case " Reservas" -> mostrarReservas();
            case " Check-in/out" -> mostrarCheckin();
        }
    }

    private void mostrarDashboard() {
        DashboardView dashboardView = new DashboardView();
        root.setCenter(dashboardView.getView());
    }

    private void mostrarQuartos() {
        QuartosView quartosView = new QuartosView();
        root.setCenter(quartosView.getView());
    }

    private void mostrarClientes() {
        ClientesView clientesView = new ClientesView();
        root.setCenter(clientesView.getView());
    }

    private void mostrarReservas() {
        ReservasView reservasView = new ReservasView();
        root.setCenter(reservasView.getView());
    }

    private void mostrarCheckin() {
        CheckInOutView checkInOutView = new CheckInOutView();
        root.setCenter(checkInOutView.getView());
    }

    private void mostrarPainelSimples(String titulo, String mensagem) {
        VBox painel = new VBox(20);
        painel.setPadding(new Insets(30));
        painel.setStyle("-fx-background-color: #1a1a2e;");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblMsg = new Label(mensagem);
        lblMsg.setStyle("-fx-font-size: 14px; -fx-text-fill: #aaaaaa;");

        painel.getChildren().addAll(lblTitulo, lblMsg);
        root.setCenter(painel);
    }

    public static void main(String[] args) {
        launch(args);
    }
}