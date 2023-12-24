import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Menu { // Classe responsável por criar a tela inicial do jogo
    private static Menu menu = null;
    private Pane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private ArrayList<Botao> botoesMenu;

    // Constrói o Menu, inicializando os objetos do Javafx e chamando os métodos
    // responsáveis por construir os elementos presentes na tela inicial.
    private Menu() {
        mainPane = new Pane();
        mainScene = new Scene(mainPane, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        botoesMenu = new ArrayList<>();
        tituloCena();
        criaBotoes();
        fundo();
        logo();
        mainStage.show();
    }

    // Método para retornar uma nova instância do Menu
    public static Menu getInstance() {
        if (menu == null) {
            menu = new Menu();
        }
        return menu;
    }

    // Método para mostrar o Menu
    public void showMenu() {
        mainStage.show();
    }

    // Método responsável por definir os parâmetros X e Y dos botões e adicioná-los
    // ao Pane e ao array de botões
    private void adicionaBotoes(Botao botao) {
        botao.setLayoutX(300);
        botao.setLayoutY(250 + botoesMenu.size() * 100);
        botoesMenu.add(botao);
        mainPane.getChildren().add(botao);
    }

    // Método para criar os botões do Menu
    public void criaBotoes() {
        botaoJogar();
        botaoRanking();
        botaoSair();
    }

    // Método para criar o botão "PLAY" do Menu e lidar com ele
    private void botaoJogar() {
        Botao botao = new Botao("PLAY");
        adicionaBotoes(botao);

        botao.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evento) {  // Se o usuário clicar no botão, esconde o stage do Menu e mostra a stage de Login
                UserLogin.getInstance().showLogin();
                mainStage.hide();
            }

        });

    }

    // Método para criar o botão "SCORE" do Menu e lidar com ele
    private void botaoRanking() { 
        Botao botao = new Botao("SCORE");
        adicionaBotoes(botao);

        botao.setOnAction(new EventHandler<ActionEvent>() {  // Se o usuário clicar no botão, esconde o stage do Menu e mostra a stage de Ranking

            @Override
            public void handle(ActionEvent evento) {
                Ranking.getInstance().showRanking();
                mainStage.hide();
            }

        });

    }

    // Método para criar o botão "EXIT" do Menu e lidar com ele
    private void botaoSair() {
        Botao botao = new Botao("EXIT");
        adicionaBotoes(botao);

        botao.setOnAction(new EventHandler<ActionEvent>() {  // Se o usuário clicar no botão, fecha o stage do Menu
            @Override
            public void handle(ActionEvent evento) {
                mainStage.close();
            }
        });

    }

    // Método para criar o fundo da tela inicial
    private void fundo() {
        Image imagemFundo = new Image("backgroundMenu.jpg", 800, 600, false, true);
        BackgroundImage fundo = new BackgroundImage(imagemFundo, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mainPane.setBackground(new Background(fundo));
    }

    // Método para criar a logo "Space Invaders" na tela inicial
    private void logo() {
        ImageView logo = new ImageView("spaceinvadersLogo.png");
        logo.setFitHeight(400);
        logo.setFitWidth(400);
        logo.setPreserveRatio(true);
        logo.setOpacity(100);
        logo.setLayoutX(190);
        logo.setLayoutY(18);
        mainPane.getChildren().add(logo);
    }

    //Método para criar o titulo da janela
    private void tituloCena() {
        mainStage.setTitle("Space Invaders Main Menu");
        mainStage.setScene(mainScene);
    }

}
