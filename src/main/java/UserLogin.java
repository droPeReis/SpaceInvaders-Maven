import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UserLogin {  // Classe responsável por criar a tela de Login do usuário (definir o nome que aparecerá no Ranking)
    private static UserLogin telaLogin = null;
    private Pane loginPane;
    private Scene loginScene;
    private Stage loginStage;
    private TextField username;
    private ArrayList<Botao> botoesLogin;

    // Constrói a tela de Login, inicializando os objetos do Javafx e chamando os métodos
    // responsáveis por construir os elementos presentes nela.
    private UserLogin() {
        loginPane = new Pane();
        loginScene = new Scene(loginPane, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
        loginStage = new Stage();
        loginStage.setScene(loginScene);
        botoesLogin = new ArrayList<>();
        tituloCena();
        fundo();
        caixaLogin();
        criaBotoes();
        loginStage.show();
    }

     // Método para retornar uma nova instância do UserLogin
    public static UserLogin getInstance() {
        if (telaLogin == null) {
            telaLogin = new UserLogin();
        }
        return telaLogin;
    }

    // Método para mostrar o Menu
    public void showLogin() {
        loginStage.show();
    }

    // Método responsável por definir os parâmetros X e Y dos botões e adicioná-los
    // ao Pane e ao array de botões
    private void adicionaBotoes(Botao botao) { 
        botao.setLayoutX(300);
        botao.setLayoutY(140 + botoesLogin.size() * 100);
        botoesLogin.add(botao);
        loginPane.getChildren().add(botao);
    }

    // Método para criar os botões do Menu
    public void criaBotoes() {
        botaoComeçar();
        botaoVoltar();
    }

    // Método para criar o botão "START" do Menu e lidar com ele
    private void botaoComeçar() {
        Botao botao = new Botao("START");
        adicionaBotoes(botao);

        botao.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent evento) {
                if (username.getText().isEmpty()) {  // Se o usuário não especificou um username, coloca "Player" como padrão
                    username.setText("Player");
                }
                SpaceInvadersGame.getInstance(new Player(username.getText())).iniciaJogo(); // Ao clicar no botão, é criada uma instância de Player e de Jogo 
                SpaceInvadersGame.getInstance().show(); // Mostra o stage do jogo
                loginStage.close();  // Fecha o stage de UserLogin
            }

        });
    }

    // Método para criar o botão "BACK" do Menu e lidar com ele
    private void botaoVoltar() {
        Botao botao = new Botao("Back");
        botao.setLayoutX(10);
        botao.setLayoutY(10);
        botao.setPrefWidth(100);
        botao.setPrefHeight(40);
        botoesLogin.add(botao);
        
        loginPane.getChildren().add(botao);

        botao.setOnAction(new EventHandler<ActionEvent>() {  // Se o usuário clicar no botão, mostra uma instância do Menu e fecha o stage de Login

            @Override
            public void handle(ActionEvent evento) {
                Menu.getInstance().showMenu();
                loginStage.close();
            }

        });
    }

    // Método para construir a "caixa de login" (Botões e TextField)
    private void caixaLogin() {
        Label label = new Label("Username:");
        label.setTextFill(Color.YELLOW);
        label.setFont(Font.font("Verdana",FontWeight.NORMAL,20));
        label.setLayoutX(255);
        label.setLayoutY(70);
        username = new TextField();
        username.setLayoutX(370);
        username.setLayoutY(70);
        loginPane.getChildren().addAll(username, label);
    }

    // Método para criar o fundo da tela de login
    private void fundo() {
        Image imagemFundo = new Image("backgroundLogin.jpg", 800, 600, false, true);
        BackgroundImage fundo = new BackgroundImage(imagemFundo, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        loginPane.setBackground(new Background(fundo));
    }

    //Método para criar o titulo da janela
    private void tituloCena() {
        loginStage.setTitle("Space Invaders Login Page");
        loginStage.setScene(loginScene);
    }

}
