import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;

/**
 * Represents the game Gun
 * 
 * @author Bernardo Copstein, Rafael Copstein
 */

public class AdvencedSpaceship extends BasicSpaceship{
    private int RELOAD_TIME = 500000000; //500000000 // Time is in nanoseconds
    private int shot_timer = 0;
    private Image image;

    public AdvencedSpaceship(int px, int py) {
        super(px, py);
        setSpeed(4);
        try {
            image = new Image("spaceshipAdvenced.png", 0, 60, true, true); // Carrega a imagem ajustando a altura para 40 pixels e mantendo proporções
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start() {
        setLimH(0, Params.WINDOW_WIDTH - 40);
        setLimV(Params.WINDOW_HEIGHT - 100, Params.WINDOW_HEIGHT);
    }

    @Override
    public void Update(long deltaTime) {
        if (jaColidiu()) {
            Game.getInstance().setGameOver();
        }
    
        setPosX(getX() + getDirH() * getSpeed());

        if (shot_timer > 0) {
            shot_timer -= deltaTime;
        }

        if (getX() >= getLMaxH()) {
            setPosX(getLMaxH() - 5);
        }

        if (getX() < getLMinH()) {
            setPosX(getLMinH() + 5);
        }

    }

    @Override
    public void OnInput(KeyCode keyCode, boolean isPressed) {
        if (keyCode == KeyCode.LEFT) {
            int dh = isPressed ? -1 : 0;
            setDirH(dh);
        }

        if (keyCode == KeyCode.RIGHT) {
            int dh = isPressed ? 1 : 0;
            setDirH(dh);
        }

        if (keyCode == KeyCode.SPACE) {
            if (shot_timer <= 0) {
                Game.getInstance().addChar(new Shot(getX() + 46, getY() - 32){
                @Override
                public void Update(long deltaTime) {
                    if (jaColidiu()) {  // Se colidiu com o inimigo o tiro desaparece
                        deactivate();
                    } else {
                        setPosY(getY() + getDirV() * getSpeed());
                        setPosX(getX() + 3);
                        if (getY() <= getLMinV() || getX() >= getLMaxH()) { // Se chegou na parte superior da tela o tiro desaparece.
                            deactivate();
                        }
                    }
                }});
                Game.getInstance().addChar(new Shot(getX() + 24, getY() - 32));
                Game.getInstance().addChar(new Shot(getX(), getY() - 32){
                    @Override
                    public void Update(long deltaTime) {
                        if (jaColidiu()) {  // Se colidiu com o inimigo o tiro desaparece
                            deactivate();
                        } else {
                            setPosY(getY() + getDirV() * getSpeed());
                            setPosX(getX() - 3);
                            if (getY() <= getLMinV() || getX() <= getLMinH()) { // Se chegou na parte superior da tela o tiro desaparece.
                                deactivate();
                            }
                        }
                    }});
                shot_timer = RELOAD_TIME;
            }
        }
        if (keyCode == KeyCode.B){
            if (shot_timer <= 0) {
            Game.getInstance().nuke();
            shot_timer = RELOAD_TIME;
            }
        }

    }

    @Override
    public int getAltura(){
        return 60;
    }

    @Override
    public int getLargura(){
        return 60;
    }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(image, getX(), getY());
    }
    
}
