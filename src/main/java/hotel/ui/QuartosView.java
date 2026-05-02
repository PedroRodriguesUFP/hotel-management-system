package hotel.ui;

import hotel.exception.FormatoInvalidoException;
import hotel.model.quartos.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class QuartosView {

    private HotelController controller = HotelController.getInstancia();
    private FlowPane grid; // Movido para cima para podermos atualizá-lo noutros métodos

    public ScrollPane getView() { // Agora retorna ScrollPane em vez de VBox
        VBox painel = new VBox(20);
        painel.setPadding(new Insets(30));
        painel.setStyle("-fx-background-color: #1a1a2e;");

        // Título
        Label titulo = new Label("🛏 Gestão de Quartos");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Subtítulo
        Label sub = new Label("Lista de todos os quartos do hotel");
        sub.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        // Formulário de Adição de Quartos
        VBox form = criarFormulario();

        // Grid de quartos
        grid = new FlowPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10, 0, 0, 0));

        atualizarGrid(); // Preenche a grid inicial

        painel.getChildren().addAll(titulo, sub, form, grid);

        // Envolver o painel no ScrollPane
        ScrollPane scroll = new ScrollPane(painel);
        scroll.setFitToWidth(true); // O conteúdo adapta-se à largura da janela
        scroll.setStyle("-fx-background: #1a1a2e; -fx-background-color: transparent;");

        return scroll;
    }

    private VBox criarFormulario() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(15));
        form.setStyle("""
                -fx-background-color: #16213e;
                -fx-background-radius: 10;
                """);

        Label lblForm = new Label("➕ Adicionar Novo Quarto");
        lblForm.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox linhaCampos = new HBox(10);

        TextField txtNumero = new TextField();
        txtNumero.setPromptText("Nº");
        txtNumero.setPrefWidth(60);

        TextField txtAndar = new TextField();
        txtAndar.setPromptText("Andar");
        txtAndar.setPrefWidth(60);

        TextField txtPreco = new TextField();
        txtPreco.setPromptText("Preço");
        txtPreco.setPrefWidth(70);

        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("Simples", "Double", "Suite");
        cbTipo.setValue("Simples");

        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setStyle("""
                -fx-background-color: #e94560;
                -fx-text-fill: white;
                -fx-cursor: hand;
                """);

        Label lblMensagem = new Label("");
        lblMensagem.setStyle("-fx-font-size: 13px;");

        btnAdicionar.setOnAction(e -> {
            try {
                int num = Integer.parseInt(txtNumero.getText());
                int andar = Integer.parseInt(txtAndar.getText());
                double preco = Double.parseDouble(txtPreco.getText());
                String tipo = cbTipo.getValue();

                // Polimorfismo e Herança
                Quarto novo = switch (tipo) {
                    case "Double" -> new QuartoDouble(num, andar, preco, true);
                    case "Suite" -> new Suite(num, andar, preco,3,true);
                    default -> new QuartoSimples(num, andar, preco,true);
                };

                controller.getHotelService().adicionarQuarto(novo);

                lblMensagem.setStyle("-fx-text-fill: #00c853;");
                lblMensagem.setText(" Quarto " + num + " (" + tipo + ") adicionado!");

                // Limpar campos
                txtNumero.clear();
                txtAndar.clear();
                txtPreco.clear();

                // Redesenhar a interface com o quarto novo
                atualizarGrid();

            } catch (NumberFormatException ex) {
                lblMensagem.setStyle("-fx-text-fill: #ff5252;");
                lblMensagem.setText(" Usa apenas números (ex: 50.5 para preço).");
            } catch (FormatoInvalidoException ex) {
                lblMensagem.setStyle("-fx-text-fill: #ff5252;");
                lblMensagem.setText(" " + ex.getMessage());
            } catch (Exception ex) {
                lblMensagem.setStyle("-fx-text-fill: #ff5252;");
                lblMensagem.setText(" Erro desconhecido.");
            }
        });

        linhaCampos.getChildren().addAll(txtNumero, txtAndar, txtPreco, cbTipo, btnAdicionar);
        form.getChildren().addAll(lblForm, linhaCampos, lblMensagem);

        return form;
    }

    private void atualizarGrid() {
        grid.getChildren().clear();
        List<Quarto> quartos = controller.getTodosQuartos();
        for (Quarto q : quartos) {
            grid.getChildren().add(criarCartaoQuarto(q));
        }
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
        Label tipo = new Label(" " + quarto.getTipoQuarto());
        tipo.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        // Andar
        Label andar = new Label(" Andar " + quarto.getAndar());
        andar.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaaaaa;");

        // Preço
        Label preco = new Label(" " + quarto.getPrecoPorNoite() + "€ / noite");
        preco.setStyle("-fx-font-size: 13px; -fx-text-fill: #e94560;");

        // Estado disponibilidade
        boolean disponivel = quarto.isDisponivel();
        Label estado = new Label(disponivel ? " Disponível" : " Ocupado");
        estado.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: "
                + (disponivel ? "#00c853" : "#ff5252") + ";");

        cartao.getChildren().addAll(numero, tipo, andar, preco, estado);
        return cartao;
    }
}