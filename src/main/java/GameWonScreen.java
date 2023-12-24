import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

public class GameWonScreen {
    private static GameWonScreen screen = null;
    private Pane pane;
    private Scene scene;
    private Stage stage;
    private ArrayList<Botao> botoes;

    private GameWonScreen() {
        pane = new Pane();
        scene = new Scene(pane, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
        stage = new Stage();
        stage.setScene(scene);
        this.botoes = new ArrayList<>();
        fundo();
        tituloCena();
        logo();
        backButton();
        stage.show();
    }

    public static GameWonScreen getInstance(int nextLevel) {
        if (screen == null) {
            screen = new GameWonScreen();
        }
        screen.createNextLevelButton(nextLevel);
        return screen;
    }

    public void show() {
        stage.show();
    }

    private void fundo() {
        Image imagemFundo = new Image("backgroundGame.jpg", 800, 600, false, true);
        BackgroundImage fundo = new BackgroundImage(imagemFundo, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(fundo));
    }

    private void logo(){
        Label sceneTitle = new Label("Level Cleared!");
        Font font = Font.font("Verdana", FontWeight.BLACK, 30);
        sceneTitle.setFont(font);
        sceneTitle.setTextFill(Color.YELLOW);
        sceneTitle.setTranslateX(280);
        sceneTitle.setTranslateY(80);
        pane.getChildren().add(sceneTitle);
    }

    private void tituloCena() {
        stage.setTitle("Level Cleared!");
        stage.setScene(scene);
    }

    private void backButton() {
        Botao botao = new Botao("Back");
        
        botao.setLayoutX(10);
        botao.setLayoutY(10);
        botao.setPrefWidth(100);
        botao.setPrefHeight(40);
        botoes.add(botao);
        pane.getChildren().add(botao);

        botao.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evento) {
                Menu.getInstance().showMenu();
                stage.close();
            }

        });
    }

    private void createNextLevelButton(int nextLevel) {
        if (nextLevel > Game.MAX_LEVEL) { // TODO nao funcionando corretamente
            return;
        }
        Botao botao = new Botao("Next Level");
        botao.setLayoutX(300);
        botao.setLayoutY(200);
        botoes.add(botao);
        pane.getChildren().add(botao);
        botao.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evento) {
                SpaceInvadersGame.getInstance().iniciaJogo(nextLevel);
                SpaceInvadersGame.getInstance().show();
                stage.close();
            }

        });
    }

}
