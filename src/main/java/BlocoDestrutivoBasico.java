import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class BlocoDestrutivoBasico extends BasicElement{

    public BlocoDestrutivoBasico(int startX,int startY){
        super(startX, startY);

    }

    @Override
    public void start() {
    }

    @Override
    public void testaColisao(Character outro) {
        if (outro instanceof BlocoDestrutivoBasico) {
            return;
        } else {
            super.testaColisao(outro);
        }
    }

    @Override
    public void Update(long deltaTime) {
        if (jaColidiu()){
            deactivate();
        }
        
    }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Paint.valueOf("#00FF00"));
        graphicsContext.fillRect(getX(), getY(), 10, 10);
        
    }
    
}