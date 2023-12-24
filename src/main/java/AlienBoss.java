import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

public class AlienBoss extends Alien1{
    private Image image;
    private static int vidas;
    private int RELOAD_TIME = 500000000;  // Time is in nanoseconds
    private int shot_timer = 0;

    public AlienBoss(int px,int py){
        super(px,py);
        try{
            // Carrega a imagem ajustando a altura para 40 pixels
            // mantendo a proporção em ambas dimensões
            image =  new Image( "alien1.png",0,40,true,true );
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
        this.vidas = 3;
        
    }

    @Override
    public void Update(long deltaTime){
        if (jaColidiu()){
            Game.getInstance().incPontos(10);
            deactivate();
        }else{
            if (shot_timer <= 0) {
                shot_timer = RELOAD_TIME;
                Game.getInstance().addChar(new EnemyShot(getX() + 46, getY() + 32){
                    @Override
                        public void Draw(GraphicsContext graphicsContext){
                            graphicsContext.setFill(Paint.valueOf("#FF0000"));
                            graphicsContext.fillOval(getX(), getY(), 8, 16);
                        }
                    @Override
                    public void Update(long deltaTime) {
                        if (jaColidiu()) {  // Se colidiu com o inimigo o tiro desaparece
                            deactivate();
                        } else {
                            setPosY(getY() + (getDirV()*-1) * getSpeed());
                            setPosX(getX() + 3);
                            if (getY() <= getLMinV() || getX() >= getLMaxH() || getX() <= getLMinH()) { // Se chegou na parte superior da tela o tiro desaparece.
                                deactivate();
                            }
                        }
                    }});
                    Game.getInstance().addChar(new EnemyShot(getX() + 24, getY() + 32){
                        @Override
                        public void Draw(GraphicsContext graphicsContext){
                            graphicsContext.setFill(Paint.valueOf("#FF0000"));
                            graphicsContext.fillOval(getX(), getY(), 8, 16);
                        }
                        public void Update(long deltaTime) {
                            if (jaColidiu()) {  // Se colidiu com o inimigo o tiro desaparece
                                deactivate();
                            } else {
                                setPosY(getY() + (getDirV()*-1) * getSpeed());
                                if (getY() <= getLMinV() || getX() >= getLMaxH() || getX() <= getLMinH()) { // Se chegou na parte superior da tela o tiro desaparece.
                                    deactivate();
                                }
                            }
                        }

                    });
                    Game.getInstance().addChar(new EnemyShot(getX(), getY() + 32){
                        @Override
                        public void Draw(GraphicsContext graphicsContext){
                            graphicsContext.setFill(Paint.valueOf("#FF0000"));
                            graphicsContext.fillOval(getX(), getY(), 8, 16);
                        }
                        @Override
                        public void Update(long deltaTime) {
                            if (jaColidiu()) {  // Se colidiu com o inimigo o tiro desaparece
                                deactivate();
                            } else {
                                setPosY(getY() + (getDirV()*-1) * getSpeed());
                                setPosX(getX() - 3);
                                if (getY() <= getLMinV() || getX() >= getLMaxH() || getX() <= getLMinH()) { // Se chegou na parte superior da tela o tiro desaparece.
                                    deactivate();
                                }
                            }
                        }});

                    shot_timer = RELOAD_TIME;

                    }


            }
            setPosX(getX() + getDirH() * (getSpeed()));
            // Se chegou no lado direito da tela ...
            if (getX() >= getLMaxH()-40 || getX() < getLMinH()){
                // Inverte a direção
                setDirH(getDirH()*-1);
                setPosY(getY()+55);
            }
            if (shot_timer > 0) shot_timer -= deltaTime;
        }

    @Override
    public void testaColisao(Character outro){
        if (outro instanceof Enemies || outro instanceof BlocoDestrutivoBasico || outro instanceof EnemyShot){
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
                if(vidas <= 0){
                    colidiu = true;
                } else{
                    vidas--;
                }
            }
        }
    }
   
}