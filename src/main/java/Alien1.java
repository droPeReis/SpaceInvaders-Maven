import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Alien1 extends BasicElement implements Enemies {
    private Image image;

    public Alien1(int px,int py){
        super(px,py);
        try{
            // Carrega a imagem ajustando a altura para 40 pixels
            // mantendo a proporção em ambas dimensões
            image =  new Image( "alien1.png",0,40,true,true );
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
        
    }

    @Override
    public void start(){
        setDirH(1);
        setSpeed(5);
    }


    @Override
    public void Update(long deltaTime){
        if (jaColidiu()){
            Game.getInstance().incPontos(1);
            deactivate();
        }else{
            setPosX(getX() + getDirH() * (getSpeed()/2));
            // Se chegou no lado direito da tela ...
            if (getX() >= getLMaxH()-40 || getX() < getLMinH()){
                // Inverte a direção
                setDirH(getDirH()*-1);
                setPosY(getY()+25);
            }
        }
    }

    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.drawImage(image, getX(),getY());
    }

    @Override
    public void testaColisao(Character outro){
        if (outro instanceof Enemies || outro instanceof BlocoDestrutivoBasico || outro instanceof EnemyShot){
            return;
        }else{
            super.testaColisao(outro);
        }
    }
    
    
}