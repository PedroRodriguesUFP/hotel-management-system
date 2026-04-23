package hotel.ui;

import hotel.exception.*;
import hotel.model.pagamentos.MetodoPagamento;
import hotel.model.reservas.Reserva;
import hotel.model.reservas.EstadoReserva;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class CheckInOutView {

    private HotelController controller = HotelController.getInstancia();

    public VBox getView() {
        VBox painel = new VBox(20);
        painel.setPadding(new Insets(30));
        painel.setStyle("-fx-background-color: #1a1a2e;");

        Label titulo = new Label("✅ Check-in / Check-out");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox listaReservas = criarListaReservas();

        painel.getChildren().addAll(titulo, listaReservas);
        return painel;
    }

    private VBox criarListaReservas() {
        VBox lista = new VBox(10);

        List<Reserva> reservas = controller.getTodasReservas();

        if (reservas.isEmpty()) {
            Label vazio = new Label("Nenhuma reserva encontrada.");
            vazio.setStyle("-fx-text-fill: #aaaaaa;");
            lista.getChildren().add(vazio);
            return lista;
        }

        for (Reserva r : reservas) {
            lista.getChildren().add(criarCartaoReserva(r));
        }

        return lista;
    }

    private VBox criarCartaoReserva(Reserva reserva) {
        VBox cartao = new VBox(10);
        cartao.setPadding(new Insets(15));
        cartao.setStyle("""
                -fx-background-color: #16213e;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                -fx-border-color: #0f3460;
                -fx-border-width: 1;
                """);

        // Info da reserva
        Label info = new Label("Reserva #" + reserva.getId()
                + " — " + reserva.getCliente().getNome()
                + " | Quarto " + reserva.getQuarto().getNumero()
                + " | " + reserva.getDataEntrada() + " → " + reserva.getDataSaida()
                + " | Total: " + reserva.getPrecoTotal() + "€");
        info.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

        // Estado
        Label estado = new Label("Estado: " + reserva.getEstado());
        estado.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: "
                + corEstado(reserva.getEstado()) + ";");

        // Mensagem feedback
        Label lblMensagem = new Label("");
        lblMensagem.setStyle("-fx-font-size: 13px;");

        // Botões
        HBox botoes = new HBox(10);

        Button btnCheckIn = new Button("✅ Check-in");
        btnCheckIn.setStyle(estiloBtn("#0f3460"));
        btnCheckIn.setDisable(reserva.getEstado() != EstadoReserva.CONFIRMADA);

        Button btnCheckOut = new Button("🚪 Check-out");
        btnCheckOut.setStyle(estiloBtn("#0f3460"));
        btnCheckOut.setDisable(reserva.getEstado() != EstadoReserva.CHECKIN);

        // ComboBox pagamento — só visível no checkout
        ComboBox<MetodoPagamento> cmbPagamento = new ComboBox<>();
        cmbPagamento.getItems().addAll(MetodoPagamento.values());
        cmbPagamento.setPromptText("Método de pagamento");
        cmbPagamento.setVisible(reserva.getEstado() == EstadoReserva.CHECKIN);
        cmbPagamento.setStyle("""
                -fx-background-color: #0f3460;
                -fx-text-fill: white;
                """);

        btnCheckIn.setOnAction(e -> {
            try {
                controller.getHotelService().fazerCheckIn(reserva.getId());
                estado.setText("Estado: " + reserva.getEstado());
                estado.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: "
                        + corEstado(reserva.getEstado()) + ";");
                lblMensagem.setStyle("-fx-text-fill: #00c853;");
                lblMensagem.setText("✅ Check-in efetuado com sucesso!");
                btnCheckIn.setDisable(true);
                btnCheckOut.setDisable(false);
                cmbPagamento.setVisible(true);
            } catch (ReservaNaoEncontradaException | CheckInInvalidoException ex) {
                lblMensagem.setStyle("-fx-text-fill: #ff5252;");
                lblMensagem.setText("❌ " + ex.getMessage());
            }
        });

        btnCheckOut.setOnAction(e -> {
            try {
                if (cmbPagamento.getValue() == null) {
                    lblMensagem.setStyle("-fx-text-fill: #ff5252;");
                    lblMensagem.setText("❌ Seleciona um método de pagamento!");
                    return;
                }

                controller.getHotelService().fazerCheckOut(reserva.getId());
                controller.getHotelService().efetuarPagamento(
                        reserva.getId(), cmbPagamento.getValue());

                estado.setText("Estado: " + reserva.getEstado());
                estado.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: "
                        + corEstado(reserva.getEstado()) + ";");
                lblMensagem.setStyle("-fx-text-fill: #00c853;");
                lblMensagem.setText("✅ Check-out e pagamento efetuados! Total: "
                        + reserva.getPrecoTotal() + "€");
                btnCheckOut.setDisable(true);
                cmbPagamento.setVisible(false);

            } catch (ReservaNaoEncontradaException | CheckOutInvalidoException
                     | ReservaNaoConcluidaException | PagamentoJaEfetuadoException ex) {
                lblMensagem.setStyle("-fx-text-fill: #ff5252;");
                lblMensagem.setText("❌ " + ex.getMessage());
            }
        });

        botoes.getChildren().addAll(btnCheckIn, btnCheckOut, cmbPagamento);
        cartao.getChildren().addAll(info, estado, botoes, lblMensagem);
        return cartao;
    }

    private String corEstado(EstadoReserva estado) {
        return switch (estado) {
            case CONFIRMADA -> "#00c853";
            case CHECKIN -> "#e94560";
            case CONCLUIDA -> "#aaaaaa";
            case CANCELADA -> "#ff5252";
            default -> "white";
        };
    }

    private String estiloBtn(String cor) {
        return "-fx-background-color: " + cor + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13px;" +
                "-fx-padding: 8 16;" +
                "-fx-background-radius: 5;" +
                "-fx-cursor: hand;";
    }
}