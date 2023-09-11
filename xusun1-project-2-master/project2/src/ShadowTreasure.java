import bagel.*;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * These codes are based on the sample solution of project1.
 */
public class ShadowTreasure extends AbstractGame {

    private final Image BACKGROUND = new Image("res/images/background.png");

    // for rounding double number
    private static DecimalFormat df = new DecimalFormat("0.00");

    // tick cycle and var
    private final int TICK_CYCLE = 10;
    private int tick;

    // list of characters
    private Player player;
    private Sandwich sandwich;
    private Zombie zombie;
    private Treasure treasure;
    private ArrayList<Entity> zombies= new ArrayList<>();
    private ArrayList<Entity> sandwiches = new ArrayList<>();
    private static ArrayList<Point> points=new ArrayList<>();

    // end of game indicator
    private boolean endOfGame;

    /**
     * constructor
     * @throws IOException
     */
    public ShadowTreasure() throws IOException {
        //super(900, 600, "Treasure Hunt");
        this.loadEnvironment("res/IO/environment.csv");
        this.tick = 1;
        this.endOfGame = false;
    }

    public Sandwich getSandwich() {
        return sandwich;
    }

    public Zombie getZombie() {
        return zombie;
    }

    /**
     * Load from input file
     */
    private void loadEnvironment(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] data = text.split(",");
                double x = Double.parseDouble(data[1]);
                double y = Double.parseDouble(data[2]);
                bagel.util.Point point=new Point(x,y);
                String check=data[0];
                if (check.equals("\uFEFFPlayer")) {
                    this.player = new Player(point, Integer.parseInt(data[3]));
                }
                if (check.equals("Zombie")) {
                    zombie=new Zombie(point);
                    zombies.add(zombie);
                }
                if (check.equals("Sandwich")) {
                    sandwich= new Sandwich(point);
                    sandwiches.add(sandwich);
                }
                if (check.equals("Treasure")) {
                    treasure=new Treasure(point);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Performs a state update.
     */
    @Override
    public void update(Input input) {
        if (this.endOfGame || input.wasPressed(Keys.ESCAPE)){
            writeToFile();
            Window.close();
        } else{
            // Draw background
            BACKGROUND.drawFromTopLeft(0, 0);
            // Update status when the TICK_CYCLE is up
            if (tick > TICK_CYCLE) {
                // update player status

                if(player.isSHOOT()){
                    player.getBullet().update(this);
                    Point point=new Point(player.getBullet().getPos().x, player.getBullet().getPos().y);
                    points.add(point);
                }
                player.update(this);
                tick = 1;

            }
            tick++;
            draw(sandwiches);
            draw(zombies);
            treasure.getImage().drawFromTopLeft(treasure.getPoint().x, treasure.getPoint().y);
            player.render();
            if(player.isSHOOT()){
                player.getBullet().render();
            }
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();

    }

    /**
     * draw the stationary entities
     * @param entities either sandwiches or zombies
     */
    public void draw(ArrayList<Entity> entities) {
        for (Entity i:entities){
            i.getImage().drawFromTopLeft(i.getPoint().x, i.getPoint().y);
        }
    }

    /**
     * write the csv file to record the positions of bullet
     */
    public static void writeToFile() {
        try (BufferedWriter writeText = new BufferedWriter(new FileWriter("res/IO/output.csv"))){
            for (Point p:points){
                writeText.write(df.format(p.x)+","  + df.format(p.y));
                writeText.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Treasure getTreasure() {
        return treasure;
    }


    public ArrayList<Entity> getSandwiches() {
        return sandwiches;
    }

    public ArrayList<Entity> getZombies() {
        return zombies;
    }

    public void setZombie(Zombie zombie) {
        this.zombie = zombie;
    }

    public void setSandwich(Sandwich sandwich) {
        this.sandwich = sandwich;
    }

    public Player getPlayer() {
        return player;
    }

    public void setEndOfGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
    }
}
