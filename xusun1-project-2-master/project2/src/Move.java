import bagel.Image;
import bagel.util.Point;

import java.text.DecimalFormat;

/**
 * Represents a moving entity in this game.
 * Either be a player for a bullet.
 */

public abstract class Move {
    // render position
    private Point pos;
    private static DecimalFormat df = new DecimalFormat("0.00");
    // direction
    private double directionX;
    private double directionY;
    public Move(Point point){
        this.pos = point;
        this.directionX = 0;
        this.directionY = 0;
    }

    /**
     * set direction to the destination
     * @param dest the entity will be reach
     */
    public void pointTo(Point dest){
        this.directionX = dest.x-this.pos.x;
        this.directionY = dest.y-this.pos.y;
        normalizeD();
    }

    /**
     * normalize direction
     */
    public void normalizeD(){
        double len = Math.sqrt(Math.pow(this.directionX,2)+Math.pow(this.directionY,2));
        this.directionX /= len;
        this.directionY /= len;
    }

    /**
     * set the new point to the moving entity
     * @param c the moving character
     */

    public void setPos(Move c) {
        this.pos = new Point(c.getPos().x + c.getStepSize() * c.getDirectionX(),
                c.getPos().y + c.getStepSize() * c.getDirectionY());

    }

    /**
     *
     * @return direction on y-axis
     */
    public double getDirectionY() {
        return directionY;
    }

    /**
     *
     * @return direction on x-axis
     */
    public double getDirectionX() {
        return directionX;
    }

    /**
     *
     * @return position
     */

    public Point getPos() {
        return pos;
    }

    /**
     *
     * @return moving step of the character
     */
    public abstract double getStepSize();
}
