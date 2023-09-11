import bagel.Image;
import bagel.util.Point;

/**
 * Represents a sandwich in this game.
 * Eating sandwich can increase player's energy.
 */
public class Sandwich extends Entity{
    // image
    private final Image image = new Image("res/images/sandwich.png");
    /**
     * constructor
     * @param point position of sandwich
     */
    public Sandwich(Point point){
        super(point);
    }

    @Override
    public Image getImage() {
        return image;
    }


}
