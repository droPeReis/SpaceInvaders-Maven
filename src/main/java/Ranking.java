import java.util.ArrayList;
import java.util.List;
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

public class Ranking {
    private static Ranking ranking = null;
    private Pane rankPane;
    private Scene rankScene;
    private Stage rankStage;
    private ArrayList<Botao> botoes;

    private Ranking() {
        rankPane = new Pane();
        rankScene = new Scene(rankPane, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
        rankStage = new Stage();
        rankStage.setScene(rankScene);
        this.botoes = new ArrayList<>();
        fundo();
        tituloCena();
        logo();
        backButton();
        displayRank();
        rankStage.show();
    }

    public static Ranking getInstance() {
        if (ranking == null) {
            ranking = new Ranking();
        }
        return ranking;
    }

    public void showRanking() {
        rankStage.show();
    }

    private void displayRank() {
        List<Player> ranking = RankingFileHandler.readRanking();
        int aux = 0;
        for (Player player : ranking) {
            Label label = new Label(player.getName() + ": " + player.getScore());
            Font font = Font.font("Verdana", FontWeight.BLACK, 30);
            label.setFont(font);
            label.setTextFill(Color.WHITE);
            label.setTranslateX(260);
            label.setTranslateY(80 + 40 * (aux++));
            rankPane.getChildren().add(label);
        }
    }
    
    private void fundo() {
        Image imagemFundo = new Image("backgroundGame.jpg", 800, 600, false, true);
        BackgroundImage fundo = new BackgroundImage(imagemFundo, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        rankPane.setBackground(new Background(fundo));
    }

    private void logo(){
        Label sceneTitle = new Label("Scoreboard");
        Font font = Font.font("Verdana", FontWeight.BLACK, 30);
        sceneTitle.setFont(font);
        sceneTitle.setTextFill(Color.YELLOW);
        sceneTitle.setTranslateX(300);
        sceneTitle.setTranslateY(30);
        rankPane.getChildren().add(sceneTitle);
    }

    private void tituloCena() {
        rankStage.setTitle("Space Invaders Score Board");
        rankStage.setScene(rankScene);
    }

    private void backButton() {
        Botao botao = new Botao("Back");
        
        botao.setLayoutX(10);
        botao.setLayoutY(10);
        botao.setPrefWidth(100);
        botao.setPrefHeight(40);
        botoes.add(botao);
        rankPane.getChildren().add(botao);

        botao.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evento) {
                Menu.getInstance().showMenu();
                rankStage.close();
            }

        });
    }

}
