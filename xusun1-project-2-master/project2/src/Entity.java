import bagel.Image;
import bagel.util.Point;

/**
 * the stationary entity(either sandwich or zombie)
 */
public abstract class Entity {
    private Point point;
    public Entity(Point point){
        this.point = point;
    }

    /**
     * get the position of entity
     * @return position
     */
    public Point getPoint() {
        return point;
    }

    /**
     *
     * @param player
     * @return whether the entity has meet the moving character
     */
    public boolean meets(Player player) {
        boolean hasMet = false;
        double distanceToPlayer = player.getPos().distanceTo(point);
        if (distanceToPlayer < player.getCloseness()) {
            hasMet = true;
        }
        return hasMet;
    }

    /**
     *
     * @return the image of the entity
     */
    public abstract Image getImage();
}
