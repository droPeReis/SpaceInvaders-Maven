import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpaceInvadersGame {  // Classe responsável por criar a tela do jogo e inicializar uma instância do mesmo
    private static SpaceInvadersGame jogo = null;
    private Pane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Player player;

    public SpaceInvadersGame() {
        gamePane = new Pane();
        gameScene = new Scene(gamePane, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        tituloCena();
        fundo();
    }

    // Constrói a tela do jogo, inicializando os objetos do Javafx e chamando os métodos
    // responsáveis por construir os elementos presentes nela.
    public static SpaceInvadersGame getInstance(Player player) {  // Recebe uma instância de Player 
        if (jogo == null) {
            jogo = new SpaceInvadersGame();
        }
        jogo.setPlayer(player);
        return jogo;
    }

    // Sobrecarga de método
    public static SpaceInvadersGame getInstance() {
        if (jogo == null) {
            jogo = new SpaceInvadersGame();
        }
        return jogo;
    }

    // Método para mostrar o stage do jogo
    public void show() {
        gameStage.show();
    }

    // Método para iniciar o jogo
    public void iniciaJogo() {
        iniciaJogo(1);
    }

    public void iniciaJogo(int level) {
        Canvas canvas = new Canvas(Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT );
        gamePane.getChildren().clear();
        gamePane.getChildren().add(canvas);
        
        Game.getInstance().loadLevel(level);
        
        // Register User Input Handler
        gameScene.setOnKeyPressed((KeyEvent event) -> {
            Game.getInstance().OnInput(event.getCode(), true);
        });
        
        gameScene.setOnKeyReleased((KeyEvent event) -> {
            Game.getInstance().OnInput(event.getCode(), false);
        });
        
        // Register Game Loop
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        new AnimationTimer(){
            long lastNanoTime = System.nanoTime();

            @Override
            public void handle(long currentNanoTime)
            {
                long deltaTime = currentNanoTime - lastNanoTime;

                Game.getInstance().Update(currentNanoTime, deltaTime);
                gc.clearRect(0, 0, Params.WINDOW_WIDTH, Params.WINDOW_HEIGHT);
                Game.getInstance().Draw(gc);

                lastNanoTime = currentNanoTime;
                Integer pontos = Game.getInstance().getPontos();
                if (Game.getInstance().isGameOver()){
                    stop();
                    RankingFileHandler.writeRanking(player.getName(), pontos);
                    goToGameOverScreen();
                } else if (Game.getInstance().isGameWon()){
                    Game.getInstance().getNextLevel();
                }
            }
            
        }.start();
        
    }

    private void fundo() { // Método para criar o fundo da tela inicial
        Image imagemFundo = new Image("backgroundGame.jpg", 800, 600, false, true);
        BackgroundImage fundo = new BackgroundImage(imagemFundo, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        gamePane.setBackground(new Background(fundo));
    }

    private void tituloCena() {
        gameStage.setTitle("Space Invaders");
        gameStage.setScene(gameScene);
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    private void goToGameOverScreen() {
        GameOverScreen.getInstance().show();
        gameStage.close();
    }

}
