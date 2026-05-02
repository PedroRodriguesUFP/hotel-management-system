package hotel.ui;

import hotel.model.quartos.Quarto;
import hotel.model.reservas.EstadoReserva;
import hotel.model.reservas.Reserva;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.List;

public class DashboardView {

    private HotelController controller = HotelController.getInstancia();

    public VBox getView() {
        VBox painel = new VBox(25);
        painel.setPadding(new Insets(30));
        painel.setStyle("-fx-background-color: #1a1a2e;");

        // Título
        Label titulo = new Label(" Dashboard");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label sub = new Label("Bem-vindo ao Hotel Management System — " + LocalDate.now());
        sub.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        // Cards de estatísticas
        HBox cards = criarCards();

        // Reservas recentes
        Label lblRecentes = new Label(" Reservas Recentes");
        lblRecentes.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox reservasRecentes = criarReservasRecentes();

        painel.getChildren().addAll(titulo, sub, cards, lblRecentes, reservasRecentes);
        return painel;
    }

    private HBox criarCards() {
        HBox cards = new HBox(15);

        List<Quarto> quartos = controller.getTodosQuartos();
        List<Reserva> reservas = controller.getTodasReservas();

        long disponiveis = quartos.stream().filter(Quarto::isDisponivel).count();
        long ocupados = quartos.stream().filter(q -> !q.isDisponivel()).count();
        long confirmadas = reservas.stream()
                .filter(r -> r.getEstado() == EstadoReserva.CONFIRMADA).count();
        long totalClientes = controller.getTodosClientes().size();

        cards.getChildren().addAll(
                criarCard(" Total Quartos", String.valueOf(quartos.size()), "#0f3460"),
                criarCard(" Disponíveis", String.valueOf(disponiveis), "#00c853"),
                criarCard(" Ocupados", String.valueOf(ocupados), "#e94560"),
                criarCard(" Reservas Ativas", String.valueOf(confirmadas), "#f5a623"),
                criarCard(" Clientes", String.valueOf(totalClientes), "#7b68ee")
        );

        return cards;
    }

    private VBox criarCard(String titulo, String valor, String cor) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setPrefWidth(160);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #16213e;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: " + cor + ";" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;");

        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: " + cor + ";");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 12px; -fx-text-fill: #aaaaaa;");
        lblTitulo.setWrapText(true);
        lblTitulo.setAlignment(Pos.CENTER);

        card.getChildren().addAll(lblValor, lblTitulo);
        return card;
    }

    private VBox criarReservasRecentes() {
        VBox lista = new VBox(8);

        List<Reserva> reservas = controller.getTodasReservas();

        if (reservas.isEmpty()) {
            Label vazio = new Label("Nenhuma reserva registada ainda.");
            vazio.setStyle("-fx-text-fill: #aaaaaa;");
            lista.getChildren().add(vazio);
            return lista;
        }

        // Mostrar últimas 5
        List<Reserva> recentes = reservas.subList(
                Math.max(0, reservas.size() - 5), reservas.size());

        for (Reserva r : recentes) {
            HBox linha = new HBox(20);
            linha.setPadding(new Insets(10, 15, 10, 15));
            linha.setAlignment(Pos.CENTER_LEFT);
            linha.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8;");

            Label id = new Label("#" + r.getId());
            id.setStyle("-fx-text-fill: #e94560; -fx-font-weight: bold;");
            id.setPrefWidth(40);

            Label cliente = new Label("👤 " + r.getCliente().getNome());
            cliente.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
            cliente.setPrefWidth(150);

            Label quarto = new Label("🛏 Quarto " + r.getQuarto().getNumero());
            quarto.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");
            quarto.setPrefWidth(110);

            Label total = new Label(r.getPrecoTotal() + "€");
            total.setStyle("-fx-text-fill: #00c853; -fx-font-weight: bold;");
            total.setPrefWidth(80);

            String corEstado = switch (r.getEstado()) {
                case CONFIRMADA -> "#00c853";
                case CHECKIN -> "#e94560";
                case CONCLUIDA -> "#aaaaaa";
                case CANCELADA -> "#ff5252";
                default -> "white";
            };

            Label estado = new Label(r.getEstado().toString());
            estado.setStyle("-fx-text-fill: " + corEstado + "; -fx-font-size: 13px;");

            linha.getChildren().addAll(id, cliente, quarto, total, estado);
            lista.getChildren().add(linha);
        }

        return lista;
    }
}