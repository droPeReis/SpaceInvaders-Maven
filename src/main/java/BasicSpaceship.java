import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

/**
 * Represents the game Gun
 * @author Bernardo Copstein, Rafael Copstein
 */

public class BasicSpaceship extends BasicElement implements KeyboardCtrl{
    private int RELOAD_TIME = 500000000;  // Time is in nanoseconds
    private int shot_timer = 0;
    private int vidas;
    private Image image;

    public BasicSpaceship(int px,int py){
        super(px,py);
        setSpeed(2);
        this.vidas = 3;
        try {
            image = new Image("spaceshipBasic.png", 0, 60, true, true); // Carrega a imagem ajustando a altura para 40 pixels e mantendo proporções
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start() {
        setLimH(20,Params.WINDOW_WIDTH-20);
        setLimV(Params.WINDOW_HEIGHT-100,Params.WINDOW_HEIGHT);
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

    public int getVidas(){
        return vidas;
    }

    @Override
    public void OnInput(KeyCode keyCode, boolean isPressed) {
        if (keyCode == KeyCode.LEFT){
            int dh = isPressed ? -1 : 0; 
            setDirH(dh);
        }
        if (keyCode == KeyCode.RIGHT){
            int dh = isPressed ? 1 : 0;
            setDirH(dh);
        }
        if (keyCode == KeyCode.SPACE){
            if (shot_timer <= 0) {
                Game.getInstance().addChar(new Shot(getX()+16,getY()-32));
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
    public void testaColisao(Character outro){
        if (outro instanceof BlocoDestrutivoBasico){
            return;
        }else{
            if (colidiu){
                deactivate();
            }
            // Monta pontos
            int p1x = this.getX();
            int p1y = this.getY();
            int p2x = p1x+this.getLargura();
            int p2y = p1y+this.getAltura();
    
            int op1x = outro.getX();
            int op1y = outro.getY();
            int op2x = op1x+outro.getLargura();
            int op2y = op1y+outro.getAltura();
    
            // Verifica colisão
            if (p1x < op2x && p2x > op1x && p1y < op2y && p2y > op1y){
                if(Game.vidas < 0){
                    colidiu = true;
                    Game.getInstance().setGameOver();
                } else{
                    Game.vidas--;
                    return;
                }
            }
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(image, getX(), getY());
    }

    
}
