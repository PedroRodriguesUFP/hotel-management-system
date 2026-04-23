package hotel.ui;

import hotel.exception.*;
import hotel.model.pessoas.Cliente;
import hotel.model.quartos.Quarto;
import hotel.model.reservas.Reserva;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.List;

public class ReservasView {

    private HotelController controller = HotelController.getInstancia();

    public VBox getView() {
        VBox painel = new VBox(20);
        painel.setPadding(new Insets(30));
        painel.setStyle("-fx-background-color: #1a1a2e;");

        Label titulo = new Label("📋 Gestão de Reservas");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox form = criarFormulario();

        Label lblLista = new Label("Reservas Registadas");
        lblLista.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox listaReservas = criarListaReservas();

        painel.getChildren().addAll(titulo, form, lblLista, listaReservas);
        return painel;
    }

    private VBox criarFormulario() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(15));
        form.setStyle("""
                -fx-background-color: #16213e;
                -fx-background-radius: 10;
                """);

        Label lblForm = new Label("➕ Nova Reserva");
        lblForm.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

        // ComboBox de clientes
        ComboBox<Cliente> cmbCliente = new ComboBox<>();
        cmbCliente.getItems().addAll(controller.getTodosClientes());
        cmbCliente.setPromptText("Selecionar Cliente");
        cmbCliente.setStyle("""
                -fx-background-color: #0f3460;
                -fx-text-fill: white;
                -fx-prompt-text-fill: #aaaaaa;
                """);
        // Mostrar nome do cliente na combobox
        cmbCliente.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Cliente c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getNome());
            }
        });
        cmbCliente.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Cliente c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getNome());
            }
        });

        // ComboBox de quartos disponíveis
        ComboBox<Quarto> cmbQuarto = new ComboBox<>();
        cmbQuarto.getItems().addAll(controller.getQuartosDisponiveis());
        cmbQuarto.setPromptText("Selecionar Quarto");
        cmbQuarto.setStyle("""
                -fx-background-color: #0f3460;
                -fx-text-fill: white;
                -fx-prompt-text-fill: #aaaaaa;
                """);
        cmbQuarto.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Quarto q, boolean empty) {
                super.updateItem(q, empty);
                setText(empty || q == null ? null :
                        "Quarto " + q.getNumero() + " - " + q.getTipoQuarto()
                                + " (" + q.getPrecoPorNoite() + "€/noite)");
            }
        });
        cmbQuarto.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Quarto q, boolean empty) {
                super.updateItem(q, empty);
                setText(empty || q == null ? null :
                        "Quarto " + q.getNumero() + " - " + q.getTipoQuarto()
                                + " (" + q.getPrecoPorNoite() + "€/noite)");
            }
        });

        // Datas
        DatePicker dpEntrada = new DatePicker();
        dpEntrada.setPromptText("Data de Entrada");
        dpEntrada.setValue(LocalDate.now());

        DatePicker dpSaida = new DatePicker();
        dpSaida.setPromptText("Data de Saída");
        dpSaida.setValue(LocalDate.now().plusDays(1));

        // Mensagem feedback
        Label lblMensagem = new Label("");
        lblMensagem.setStyle("-fx-font-size: 13px;");

        // Botão
        Button btnReservar = new Button("Fazer Reserva");
        btnReservar.setStyle("""
                -fx-background-color: #e94560;
                -fx-text-fill: white;
                -fx-font-size: 13px;
                -fx-padding: 8 16;
                -fx-background-radius: 5;
                -fx-cursor: hand;
                """);

        btnReservar.setOnAction(e -> {
            try {
                Cliente cliente = cmbCliente.getValue();
                Quarto quarto = cmbQuarto.getValue();

                if (cliente == null || quarto == null) {
                    lblMensagem.setStyle("-fx-text-fill: #ff5252;");
                    lblMensagem.setText("❌ Seleciona um cliente e um quarto!");
                    return;
                }

                Reserva reserva = controller.getHotelService().fazerReserva(
                        cliente, quarto,
                        dpEntrada.getValue(),
                        dpSaida.getValue()
                );

                lblMensagem.setStyle("-fx-text-fill: #00c853;");
                lblMensagem.setText("✅ Reserva #" + reserva.getId() + " criada! Total: "
                        + reserva.getPrecoTotal() + "€");

                // Atualizar quartos disponíveis
                cmbQuarto.getItems().clear();
                cmbQuarto.getItems().addAll(controller.getQuartosDisponiveis());

            } catch (QuartoIndisponivelException | DataInvalidaException ex) {
                lblMensagem.setStyle("-fx-text-fill: #ff5252;");
                lblMensagem.setText("❌ " + ex.getMessage());
            }
        });

        HBox linha1 = new HBox(10, cmbCliente, cmbQuarto);
        HBox linha2 = new HBox(10, dpEntrada, dpSaida);

        form.getChildren().addAll(lblForm, linha1, linha2, btnReservar, lblMensagem);
        return form;
    }

    private VBox criarListaReservas() {
        VBox lista = new VBox(8);

        List<Reserva> reservas = controller.getTodasReservas();

        if (reservas.isEmpty()) {
            Label vazio = new Label("Nenhuma reserva registada.");
            vazio.setStyle("-fx-text-fill: #aaaaaa;");
            lista.getChildren().add(vazio);
            return lista;
        }

        for (Reserva r : reservas) {
            HBox linha = new HBox(20);
            linha.setPadding(new Insets(10, 15, 10, 15));
            linha.setStyle("""
                    -fx-background-color: #16213e;
                    -fx-background-radius: 8;
                    """);

            Label id = new Label("#" + r.getId());
            id.setStyle("-fx-text-fill: #e94560; -fx-font-weight: bold; -fx-font-size: 13px;");
            id.setPrefWidth(30);

            Label cliente = new Label("👤 " + r.getCliente().getNome());
            cliente.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
            cliente.setPrefWidth(130);

            Label quarto = new Label("🛏 Quarto " + r.getQuarto().getNumero());
            quarto.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");
            quarto.setPrefWidth(100);

            Label datas = new Label(r.getDataEntrada() + " → " + r.getDataSaida());
            datas.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");
            datas.setPrefWidth(180);

            Label total = new Label(r.getPrecoTotal() + "€");
            total.setStyle("-fx-text-fill: #00c853; -fx-font-size: 13px; -fx-font-weight: bold;");
            total.setPrefWidth(70);

            Label estado = new Label(r.getEstado().toString());
            estado.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");

            linha.getChildren().addAll(id, cliente, quarto, datas, total, estado);
            lista.getChildren().add(linha);
        }

        return lista;
    }
}