import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

public class Alien3 extends BasicElement implements Enemies {
    private static int vidas = 3;
    private Image image;
    private static int RELOAD_TIME = 200;

    public Alien3(int px,int py){
        super(px,py);
        try{
            // Carrega a imagem ajustando a altura para 40 pixels
            // mantendo a proporção em ambas dimensões
            image =  new Image( "alien3.png",0,40,true,true );
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void start(){
        setDirH(1);
        setSpeed(6);
    }


    @Override
    public void Update(long deltaTime){
        if(vidas == 0){
            setColidiu();
        }
        if (jaColidiu()){
            Game.getInstance().incPontos(3);
            deactivate();
        }else{
            if(RELOAD_TIME <= 0){
                Game.getInstance().addChar(new EnemyShot(getX(), getY() + 40){
                    @Override
                        public void Draw(GraphicsContext graphicsContext){
                            graphicsContext.setFill(Paint.valueOf("#FFFFFF"));
                            graphicsContext.fillOval(getX(), getY(), 8, 16);
                        }
                    @Override
                    public void Update(long deltaTime) {
                        setDirV(1);
                        if (jaColidiu()) {  // Se colidiu com o inimigo o tiro desaparece
                            deactivate();
                        } else {
                            setPosY(getY() + getDirV() * getSpeed());
                            if (getY() >= getLMaxH()) { // Se chegou na parte superior da tela o tiro desaparece.
                                deactivate();
                            }
                        }
                    }});
                RELOAD_TIME = 200;
            }
            RELOAD_TIME--;
            setPosX(getX() + getDirH() * (getSpeed()/2));
            if(getX() >= getLMaxH()-40){
                setPosX(getLMinH());
                setPosY(getY()+45);
            }
            if(getX() < getLMinH()){
                setPosX(getLMaxH()-40);
                setPosY(getY()+45);
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
        }
        else{
            super.testaColisao(outro);
        }
    }
    
}