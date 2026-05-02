package hotel.ui;

import hotel.exception.ClienteJaExisteException;
import hotel.exception.FormatoInvalidoException;
import hotel.model.pessoas.Cliente;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class ClientesView {

    private HotelController controller = HotelController.getInstancia();

    public VBox getView() {
        VBox painel = new VBox(20);
        painel.setPadding(new Insets(30));
        painel.setStyle("-fx-background-color: #1a1a2e;");

        Label titulo = new Label(" Gestão de Clientes");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Formulário de registo
        VBox form = criarFormulario();

        // Tabela de clientes
        Label lblLista = new Label("Clientes Registados");
        lblLista.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox listaClientes = criarListaClientes();

        painel.getChildren().addAll(titulo, form, lblLista, listaClientes);
        return painel;
    }

    private VBox criarFormulario() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(15));
        form.setStyle("""
                -fx-background-color: #16213e;
                -fx-background-radius: 10;
                """);

        Label lblForm = new Label(" Registar Novo Cliente");
        lblForm.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Campos
        TextField txtNome = criarCampo("Nome completo");
        TextField txtEmail = criarCampo("Email");
        TextField txtTelefone = criarCampo("Telefone");
        TextField txtNif = criarCampo("NIF");
        TextField txtNacionalidade = criarCampo("Nacionalidade");

        // Mensagem de feedback
        Label lblMensagem = new Label("");
        lblMensagem.setStyle("-fx-font-size: 13px;");

        // Botão
        Button btnRegistar = new Button("Registar Cliente");
        btnRegistar.setStyle("""
                -fx-background-color: #e94560;
                -fx-text-fill: white;
                -fx-font-size: 13px;
                -fx-padding: 8 16;
                -fx-background-radius: 5;
                -fx-cursor: hand;
                """);

        btnRegistar.setOnAction(e -> {
            try {
                Cliente novo = new Cliente(
                        txtNome.getText(),
                        txtEmail.getText(),
                        txtTelefone.getText(),
                        txtNif.getText(),
                        txtNacionalidade.getText()
                );
                controller.getHotelService().registarCliente(novo);

                lblMensagem.setStyle("-fx-text-fill: #00c853; -fx-font-size: 13px;");
                lblMensagem.setText(" Cliente registado com sucesso!");

                // Limpar campos
                txtNome.clear();
                txtEmail.clear();
                txtTelefone.clear();
                txtNif.clear();
                txtNacionalidade.clear();

            } catch (ClienteJaExisteException | FormatoInvalidoException ex) {
                lblMensagem.setStyle("-fx-text-fill: #ff5252; -fx-font-size: 13px;");
                lblMensagem.setText(" " + ex.getMessage());
            }
        });

        HBox linha1 = new HBox(10, txtNome, txtEmail, txtTelefone);
        HBox linha2 = new HBox(10, txtNif, txtNacionalidade);

        form.getChildren().addAll(lblForm, linha1, linha2, btnRegistar, lblMensagem);
        return form;
    }

    private VBox criarListaClientes() {
        VBox lista = new VBox(8);

        List<Cliente> clientes = controller.getTodosClientes();

        if (clientes.isEmpty()) {
            Label vazio = new Label("Nenhum cliente registado.");
            vazio.setStyle("-fx-text-fill: #aaaaaa;");
            lista.getChildren().add(vazio);
            return lista;
        }

        for (Cliente c : clientes) {
            HBox linha = new HBox(20);
            linha.setPadding(new Insets(10, 15, 10, 15));
            linha.setStyle("""
                    -fx-background-color: #16213e;
                    -fx-background-radius: 8;
                    """);

            Label nome = new Label(" " + c.getNome());
            nome.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold;");
            nome.setPrefWidth(150);

            Label email = new Label(" " + c.getEmail());
            email.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");
            email.setPrefWidth(180);

            Label nif = new Label(" " + c.getNif());
            nif.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");

            linha.getChildren().addAll(nome, email, nif);
            lista.getChildren().add(linha);
        }

        return lista;
    }

    private TextField criarCampo(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setPrefWidth(180);
        field.setStyle("""
                -fx-background-color: #0f3460;
                -fx-text-fill: white;
                -fx-prompt-text-fill: #aaaaaa;
                -fx-padding: 8;
                -fx-background-radius: 5;
                """);
        return field;
    }
}