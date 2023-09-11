import bagel.Image;
import bagel.util.Point;

/**
 * Represents a zombie in this game.
 * A zombie can be killed by player.
 */
public class Zombie extends Entity{

    // image and type
    private final Image image = new Image("res/images/zombie.png");

    /**
     * constructor
     * @param point position of the zombie
     */
    public Zombie(Point point) {
        super(point);
    }

    @Override
    public Image getImage() {
        return image;
    }

}
