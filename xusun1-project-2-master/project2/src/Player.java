import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Represents a player in this game.
 * A player can make movements, eat sandwiches and shoot bullets to kill zombies.
 */
public class Player extends Move{

    // image source file
    public static final String FILENAME = "res/images/player.png";
    // speed
    public static final double STEP_SIZE = 10;
    // healthbar font
    private final Font FONT = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();
    private final int CLOSENESS=50;
    // image and type
    private final Image image;
    // render position
    private Bullet bullet;
    private boolean SHOOT;
    private final int SHOOTING_RANGE=150;
    private static DecimalFormat df = new DecimalFormat("0.00");
    // healthbar parameters
    private int energy;
    private boolean moreZombies;

    /**
     *constructor
     * @param point assign player's position
     * @param energy assign player's energy
     */

    public Player(Point point, int energy) {
        super(point);
        this.image = new Image(FILENAME);
        this.energy = energy;
        this.SHOOT=false;
        this.moreZombies=false;
    }

    /**
     *
     * @param tomb the environment
     */
    public void update(ShadowTreasure tomb) {
        // Check if the player meets the Zombie and if so reduce energy by 3 and
        // terminate. Otherwise if the player meets the Sandwich increase the energy
        // an set the Sandwich to invisible
        if ((this.getPos()).distanceTo(tomb.getTreasure().getPoint()) <= CLOSENESS) {
            FONT.drawString("energy: " + this.energy, 20, 760, OPT.setBlendColour(Colour.BLACK));
            System.out.println( this.energy+ ",Success!");
            tomb.setEndOfGame(true);
        } else {
            if ((tomb.getSandwich()!=null)&&(tomb.getSandwich().meets(this))) {
                eatSandwich();
                tomb.getSandwiches().remove(tomb.getSandwich());
                tomb.setSandwich(null);
            } else if (checkZombie(tomb) != null) {
                //shoot
                if (!SHOOT) {
                    SHOOT=true;
                    bullet = new Bullet(this.getPos());
                    energy -= 3;
                }
            }
            if (tomb.getZombies().isEmpty()) {
                this.pointTo(tomb.getTreasure().getPoint());
            } else if ((energy <3)&&(!tomb.getSandwiches().isEmpty())) {
                tomb.setSandwich((Sandwich) findTheNearest(tomb.getSandwiches()));
                this.pointTo(tomb.getSandwich().getPoint());

            } else  {
                if (energy<3){
                    moreZombies=true;
                }
                tomb.setZombie((Zombie) findTheNearest(tomb.getZombies()));
                this.pointTo(tomb.getZombie().getPoint());
            }
            this.setPos(this);

        }
    }

    /**
     * find the zombie within the shooting range
     * @param tomb the environment
     * @return  the Zombie can be shot by player
     */
    public Zombie checkZombie(ShadowTreasure tomb){
        double len;
        for(Entity i:tomb.getZombies()){
            len=this.getPos().distanceTo(i.getPoint());
            if (len<SHOOTING_RANGE){
                return (Zombie) i;
            }
        }
        return null;
    }

    /**
     * find the closest entity
     * @param entities zombies or sandwiches in the tomb
     * @return nearest zombie or sandwich player can reach
     */
    public Entity findTheNearest(ArrayList<Entity> entities) {
        double len = 10000;
        double dis;
        Entity z = null;
        for (Entity i : entities) {
            dis = (getPos()).distanceTo(i.getPoint());
            if (len > dis) {
                len = dis;
                z = i;
            }
        }
        return z;
    }

    /**
     * draw the player's image and energy
     */
    public void render() {
        image.drawFromTopLeft(getPos().x, getPos().y);
        // also show energy level
        FONT.drawString("energy: "+ energy,20,760, OPT.setBlendColour(Colour.BLACK));
    }

    /**
     * energy change after eating sandwich
     */
    public void eatSandwich(){
        energy += 5;
    }

    public int getCloseness() {
        return CLOSENESS;
    }


    public boolean isSHOOT() {
        return SHOOT;
    }

    public Bullet getBullet() {
        return bullet;
    }


    public void setSHOOT(boolean SHOOT) {
        this.SHOOT = SHOOT;
    }

    public boolean isMoreZombies() {
        return moreZombies;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public double getStepSize() {
        return STEP_SIZE;
    }
}
