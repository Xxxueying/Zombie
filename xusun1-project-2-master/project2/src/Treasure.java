import bagel.Image;
import bagel.util.Point;

/**
 * Represents a treasure in this game.
 * Reaching the treasure is the final purpose of this game.
 */
public class Treasure extends Entity{
    private static final Image image = new Image("res/images/treasure.png");

    /**
     * constructor
     * @param point position of treasure
     */
    public Treasure(Point point){
        super(point);
    }

    @Override
    public Image getImage() {
        return image;
    }
}
