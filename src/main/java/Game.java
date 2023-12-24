import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.List;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 * Handles the game lifecycle and behavior
 * 
 * @author Bernardo Copstein and Rafael Copstein
 */

public class Game {
    private static Game game = null;
    public static final int MAX_LEVEL = 3;
    private BasicSpaceship nave;
    private List<Character> activeChars;
    private boolean gameOver;
    private int pontos;
    private LocalDate ld;
    private int fase;
    public static int vidas;

    private Game() {
        gameOver = false;
        pontos = 0;
        ld = LocalDate.now();
        fase = 3;
    }

    public LocalDate getDate() {
        return ld;
    }

    public void setGameOver() {
        gameOver = true;
    }

    public boolean isGameOver() {
        if (vidas < 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isGameWon() {
        return areAllEnemiesDead();
    }

    private boolean areAllEnemiesDead() {
        return !activeChars
                .stream()
                .anyMatch(c -> c instanceof Enemies);
    }

    public int getPontos() {
        return pontos;
    }

    public void incPontos(int x) {
        this.pontos++;
    }

    public int getVidas() {
        return vidas;
    }

    public void decVidas() {
        vidas--;
        if (isGameOver()) {
            activeChars.clear();
        }
    }

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return (game);
    }

    public void addChar(Character c) {
        activeChars.add(c);
        c.start();
    }

    public void eliminate(Character c) {
        activeChars.remove(c);
    }

    public void nuke() { // para acelerar a apresentação do trabalho
        for (Character c : activeChars) {
            if (c instanceof Enemies) {
                eliminate(c);
            }
        }
    }

    public int getNextLevel() {
        return fase + 1;
    }

    public void loadLevel(int level) {
        fase = level;
        Start();
    }

    public void Start() {
        this.gameOver = false;
        Game.vidas = 3;
        // Repositório de personagens
        activeChars = new LinkedList<>();
        // Adiciona a nave
        nave = new BasicSpaceship(400, 550);
        activeChars.add(nave);
        // Adiciona as barreiras
        addBarreiras();
        // inimigos iniciais
        int quantEnemis = 8;
        for (int i = 0; i < quantEnemis; i++) {
            activeChars.add(new Alien1(i * 40, 50));
        }

        for (Character c : activeChars) {
            c.start();
        }
    }

    public void show() {
        game.show();
    }

    private void addLevel2Enemies() {
        addAliens1(15, 40, 40);
        addAliens2(3, 80, 80);
    }

    private void addLevel3Enemies() {
        addAliens1(15, 40, 50);
        addAliens1(15, 40, 120);
        addAliens3(5, 80, 180);
    }

    private void addLevel4Enemies() {
        addAliens2(5, 140, 40);
    }

    private void addLevel5Enemies() {
        for (int i = 0; i < 8; i++) {
            activeChars.add(new Alien2(i * 40, 50));
        }
    }

    private void addAliens1(int qnt, int x, int y) {
        for (int i = 0; i < qnt; i++) {
            activeChars.add(new Alien1(i * 40, y));
        }
    }

    private void addAliens2(int qnt, int x, int y) {
        for (int i = 0; i < qnt; i++) {
            activeChars.add(new Alien2(i * 40, y));
        }
    }

    private void addAliens3(int qnt, int x, int y) {
        for (int i = 0; i < qnt; i++) {
            activeChars.add(new Alien3(i * 80, y));
        }
    }

    private void addLevelBossEnemies(int x, int y) {
        activeChars.add(new AlienBoss(x, y));
    }

    public void addBarreiras() {
        for (int j = 0; j < 60; j = j + 10) {
            for (int i = 0; i < 60; i = i + 11) {
                activeChars.add(new BlocoDestrutivoBasico(40 + i, 400 + j));
            }
        }
        for (int j = 0; j < 60; j = j + 10) {
            for (int i = 0; i < 60; i = i + 11) {
                activeChars.add(new BlocoDestrutivoBasico(370 + i, 400 + j));
            }
        }
        for (int j = 0; j < 60; j = j + 10) {
            for (int i = 0; i < 60; i = i + 11) {
                activeChars.add(new BlocoDestrutivoBasico(680 + i, 400 + j));
            }
        }
    }

    public void Update(long currentTime, long deltaTime) {
        if (Game.vidas < 0) {
            return;
        }
    
        if (areAllEnemiesDead()) {
            nextLevel();
        }

        for (int i = 0; i < activeChars.size(); i++) {
            Character este = activeChars.get(i);
            este.Update(deltaTime);
            for (int j = 0; j < activeChars.size(); j++) {
                Character outro = activeChars.get(j);
                if (este != outro) {
                    este.testaColisao(outro);
                }
            }
        }
    }

    private void nextLevel() {
        if (fase == 1) {
            addLevel2Enemies();
        }
        if (fase == 2) {
            addLevel3Enemies();
        }
        if (fase == 3) {
            addLevel4Enemies();
        }
        if (fase == 4) {
            activeChars.clear();
            nave = new AdvencedSpaceship(400, 550);
            activeChars.add(nave);
            addBarreiras();
            addLevel5Enemies();
        }
        if (fase == 5) {
            addLevelBossEnemies(40, 40);
        }

        if (fase == 6) {
            addLevelBossEnemies(430, 40);
        }

        if (fase > 6) {
            int a = Params.getInstance().nextInt(15);
            int b = Params.getInstance().nextInt(15);
            int c = Params.getInstance().nextInt(15);
            addAliens1(a, 40, 40);
            addAliens2(b, 40, 120);
            addAliens3(c, 40, 160);
            addLevelBossEnemies(40, 40);
        }

        for (Character c : activeChars) {
            c.start();
        }
        fase++;
    }

    public void OnInput(KeyCode keyCode, boolean isPressed) {
        nave.OnInput(keyCode, isPressed);
    }

    public void Draw(GraphicsContext graphicsContext) {
        drawPoint(graphicsContext);
        drawLifes(graphicsContext);
        drawLevel(graphicsContext);
        for (Character c : activeChars) {
            c.Draw(graphicsContext);
        }
    }

    private void drawPoint(GraphicsContext graphicsContext) {
        graphicsContext.setFont(new Font("Verdana", 20));
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillText("Pontos: " + getPontos(), 10, 20);
    }

    private void drawLifes(GraphicsContext graphicsContext) {
        graphicsContext.setFont(new Font("Verdana", 20));
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillText("Vidas: " + Game.vidas, 10, 40);
    }

    private void drawLevel(GraphicsContext graphicsContext) {
        graphicsContext.setFont(new Font("Verdana", 20));
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillText("Lvl: " + fase, 10, 60);
    }

    public int activeCharsSize() {
        return activeChars.size();
    }

}
