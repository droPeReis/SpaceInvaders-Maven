public class EnemyShot extends Shot{

    public EnemyShot(int px, int py) {
        super(px, py);
    }

    @Override
    public void testaColisao(Character outro) {
        if (outro instanceof Enemies || outro instanceof Shot){
            return;
        }else{
            super.testaColisao(outro);
        }
    }

}
