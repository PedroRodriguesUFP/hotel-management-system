package hotel.ui;

import hotel.model.quartos.Quarto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

public class QuartosView {

    private HotelController controller = HotelController.getInstancia();

    public VBox getView() {
        VBox painel = new VBox(20);
        painel.setPadding(new Insets(30));
        painel.setStyle("-fx-background-color: #1a1a2e;");

        // Título
        Label titulo = new Label("🛏 Gestão de Quartos");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Subtítulo
        Label sub = new Label("Lista de todos os quartos do hotel");
        sub.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        // Grid de quartos
        FlowPane grid = new FlowPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10, 0, 0, 0));

        List<Quarto> quartos = controller.getTodosQuartos();

        for (Quarto q : quartos) {
            grid.getChildren().add(criarCartaoQuarto(q));
        }

        painel.getChildren().addAll(titulo, sub, grid);
        return painel;
    }

    private VBox criarCartaoQuarto(Quarto quarto) {
        VBox cartao = new VBox(8);
        cartao.setPadding(new Insets(15));
        cartao.setPrefWidth(200);
        cartao.setStyle("""
                -fx-background-color: #16213e;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                -fx-border-color: #0f3460;
                -fx-border-width: 1;
                """);

        // Número do quarto
        Label numero = new Label("Quarto " + quarto.getNumero());
        numero.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Tipo
        Label tipo = new Label("📌 " + quarto.getTipoQuarto());
        tipo.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        // Andar
        Label andar = new Label("🏢 Andar " + quarto.getAndar());
        andar.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        // Preço
        Label preco = new Label("💶 " + quarto.getPrecoPorNoite() + "€ / noite");
        preco.setStyle("-fx-font-size: 13px; -fx-text-fill: #e94560;");

        // Estado disponibilidade
        boolean disponivel = quarto.isDisponivel();
        Label estado = new Label(disponivel ? "✅ Disponível" : "❌ Ocupado");
        estado.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: "
                + (disponivel ? "#00c853" : "#ff5252") + ";");

        cartao.getChildren().addAll(numero, tipo, andar, preco, estado);
        return cartao;
    }
}