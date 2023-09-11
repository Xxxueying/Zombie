import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;


/**
 * Represents a bullet in this game.
 * A bullet can kill zombies.
 */

public class Bullet extends Move{
    private static final Image image=new Image("res/images/shot.png");
    private static final int STEP_SIZE=25;
    private static final int KILL_RANGE=25;
    private int bulletStep;

    /**
     * constructor
     * @param point the position
     */
    public Bullet(Point point){
        super(point);
        bulletStep=0;
    }

    /**
     * update the environment
     * @param tomb the environment
     */
    public void update(ShadowTreasure tomb) {
        double d;
            pointTo(tomb.getZombie().getPoint());
            setPos(this);
            d=(getPos()).distanceTo(tomb.getZombie().getPoint());
            if (d<KILL_RANGE) {
                if(tomb.getPlayer().isMoreZombies()){
                    System.out.println(tomb.getPlayer().getEnergy());
                    tomb.setEndOfGame(true);
                }
                tomb.getZombies().remove(tomb.getZombie());
                tomb.getPlayer().setSHOOT(false);
            }

    }

    @Override
    public double getStepSize(){return STEP_SIZE;}

    /**
     * draw the image of the bullet
     */
    public void render() {
        image.drawFromTopLeft(getPos().x, getPos().y);
    }
}
