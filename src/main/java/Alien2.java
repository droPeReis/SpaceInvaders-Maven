import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Alien2 extends BasicElement implements Enemies{
    private Image image;
    public Alien2(int px,int py){
        super(px,py);
        try{
            // Carrega a imagem ajustando a altura para 40 pixels
            // mantendo a proporção em ambas dimensões
            image =  new Image( "alien2.png",0,40,true,true );
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start(){
        setDirH(1);
        setSpeed(7);
    }

    @Override
    public void Update(long deltaTime){
        if (jaColidiu()){
            Game.getInstance().incPontos(2);
            deactivate();
        }else{
            setPosY(getY() + 1);
            setPosX(getX() + getDirH() * (getSpeed()/2));
            // Se chegou no lado direito da tela ...
            if (getX() >= getLMaxH() || getX() < getLMinH()){
                setDirH(getDirH()*-1);
            }
        }
    }

    @Override
    public void testaColisao(Character outro){
        if(getY()>=600){
            Game.vidas --;
        }
        if (outro instanceof Enemies || outro instanceof BlocoDestrutivoBasico || outro instanceof EnemyShot){
            return;
        }else{
            super.testaColisao(outro);
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.drawImage(image, getX(),getY());
    }

}