import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;

/**
 * An example Bagel game.
 */
public class ShadowTreasure extends AbstractGame {

    private final Font font = new Font("res/font/DejaVuSans-Bold.ttf",20);

    private static final double STEP_SIZE = 10;
    private static final double MEET_DISTANCE = 50;
    private static final double SHOOT_DISTANCE = 150;
    private static final double SHOT_DEAD_RANGE = 25;
    private static final double BULLET_STEP_SIZE = 25;
    private static final double MAX_DIST = 2000;

    private final Background background = new Background();
    private Player player = null;
    private Treasure treasure;
    private final Bullet bullet = new Bullet(0,0);

    private final ArrayList<Zombie> zombieList = new ArrayList<>();
    private final ArrayList<Sandwich> sandwichList =  new ArrayList<>();

    private static final Point ENERGY_POINT = new Point(20, 760);
    private static int tick = 0;


    // for rounding double number; use this to print the location of the player
    private final static DecimalFormat df = new DecimalFormat("0.00");

    /**
     * @param y y value to print
     * @param x x value to print
     */
    public void printToFile(double x, double y){
        try (PrintWriter pw =
                     new PrintWriter(new FileWriter("res/IO/output.csv"))) {

            pw.println(df.format(x) + "," + df.format(y));

        } catch (IOException e) { e.printStackTrace();
        }
    }

    /**
     * Constructor loads the environment
     * @throws IOException throws IO exception
     */
    public ShadowTreasure() throws IOException {
        this.loadEnvironment("res/IO/environment.csv");
    }

    /**
     * Load from input file
     */
    private void loadEnvironment(String filename){
        // Code here to read from the file and set up the environment
        ArrayList<String> environment = new ArrayList<>();
        try (BufferedReader br =
                     new BufferedReader(new FileReader(filename))) {
            String text;
            while ((text = br.readLine()) != null) {
                environment.add(text);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Initialise classes by reading the first String and imputing its data respectively

        Sandwich tempSandwich;
        Zombie tempZombie;

        for (String s : environment) {
            String[] entities = s.split(",");

            if (player == null) {
                player = new Player(Double.parseDouble(entities[1]), Double.parseDouble(entities[2]),
                        Integer.parseInt(entities[3]));
            }
            if (entities[0].equals("Treasure")) {
                treasure = new Treasure(Double.parseDouble(entities[1]), Double.parseDouble(entities[2]));
            }
            if (entities[0].equals("Sandwich")) {
                tempSandwich = new Sandwich(Double.parseDouble(entities[1]), Double.parseDouble(entities[2]));
                sandwichList.add(tempSandwich);
            }
            if (entities[0].equals("Zombie")) {
                tempZombie = new Zombie(Double.parseDouble(entities[1]), Double.parseDouble(entities[2]));
                zombieList.add(tempZombie);
            }
        }
    }

    /**
     * method which returns the closest zombie or if there is none alive it returns null
     * @param zombieList Stores the zombies
     * @return Point of the zombie which is the closest to the player
     */
    public Point closestZombie(ArrayList<Zombie> zombieList){
        Point closestZombie = null;
        double lowestDistance = MAX_DIST;
        for(Zombie zombies: zombieList){
            if(zombies.getZombieStatus() && zombies.distanceToPlayer(player) <= lowestDistance){
                lowestDistance = zombies.distanceToPlayer(player);
                closestZombie = zombies.getPosition();
            }
        }
        return closestZombie;
    }

    /**
     * @param zombieList Stores all the zombies
     * @return the zombie which is closest to the bullet
     */
    public Zombie closestZombieToBullet(ArrayList<Zombie> zombieList){
        Zombie closestZombie = null;
        double lowestDistance = MAX_DIST;
        for(Zombie zombies: zombieList){
            if(!zombies.isShot() && zombies.distanceToBullet(bullet) <= lowestDistance){
                lowestDistance = zombies.distanceToBullet(bullet);
                closestZombie = zombies;
            }
        }
        return closestZombie;
    }

    /**
     * method which returns the closest sandwich or if there are none left it returns null
     * @param sandwichList Stores sandwiches
     * @return the Point of the closest sandwich to the player
     */
    public Point closestSandwich(ArrayList<Sandwich> sandwichList){
        Point closestSandwich = null;
        double lowestDistance = MAX_DIST;
        for(Sandwich sandwiches: sandwichList){
            if(sandwiches.getSandwichEnergy() && sandwiches.distanceToPlayer(player) <= lowestDistance){
                lowestDistance = sandwiches.distanceToPlayer(player);
                closestSandwich = sandwiches.getPosition();
            }
        }
        return closestSandwich;
    }

    /**
     * Checks if the player meets a sandwich
     * @param player the player
     * @param sandwichList stores the sandwiches
     * @return the sandwich that is met by the player
     */
    public Sandwich meetsSandwich(ArrayList<Sandwich> sandwichList, Player player){
        for(Sandwich sandwiches: sandwichList){
            if(sandwiches.getSandwichEnergy() && sandwiches.distanceToPlayer(player) < MEET_DISTANCE){
                return sandwiches;
            }
        }
        return null;
    }

    /**
     * Checks if the player meets a Zombie that is still alive
     * @param zombieList Stores the zombies
     * @param player The player
     * @return the zombie that is met by the player
     */
    public Zombie meetsZombie(ArrayList<Zombie> zombieList, Player player){
        for(Zombie zombies: zombieList){
            if(zombies.getZombieStatus() && zombies.distanceToPlayer(player) < SHOOT_DISTANCE){
                return zombies;
            }
        }
        return null;
    }

    /**
     * Checks if any zombies are still alive
     * @param zombieList stores the zombies
     * @return true if zombies are still alive
     */
    public boolean areZombiesAlive(ArrayList<Zombie> zombieList){
        for(Zombie zombies: zombieList){
            if(zombies.getZombieStatus()){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there are any sandwiches left
     * @param sandwichList stores the sandwiches
     * @return true if any sandwiches are left in the tomb
     */
    public boolean isSandwichLeft(ArrayList<Sandwich> sandwichList){
        for(Sandwich sandwiches: sandwichList){
            if(sandwiches.getSandwichEnergy()){
                return true;
            }
        }
        return false;
    }

    /**
     * Performs a state update.
     */
    @Override
    public void update(Input input) {
        // Logic to update the game, as per specification must go here
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        if(tick%10 == 0) {
            //Player only moves every 10 frames

            if (treasure.distanceToPlayer(player) < MEET_DISTANCE ||
                    (player.getEnergy() < 3 && areZombiesAlive(zombieList) && !isSandwichLeft(sandwichList))) {
                System.out.print(player.getEnergy());

                if (treasure.distanceToPlayer(player) < MEET_DISTANCE) {
                    System.out.print(",Success!");
                }

                Window.close();
                return;
            }

            if(meetsSandwich(sandwichList, player) != null){
                //player meets a sandwich
                player.setEnergy(player.getEnergy() + 5);
                meetsSandwich(sandwichList, player).setSandwichEnergy(false);

            } else if(meetsZombie(zombieList, player) != null){
                //player meets a zombie
                player.setEnergy(player.getEnergy() - 3);
                meetsZombie(zombieList, player).setZombieStatus(false);


                //bullet gets fired
                //set the position of the bullet and its destination

                bullet.setFired(true);
                bullet.setBulletX(player.getPosition().x);
                bullet.setBulletY(player.getPosition().y);
                bullet.setBulletDirectionTo(closestZombieToBullet(zombieList).getPosition());

            }

            //bullet fired under these conditions
            if(bullet.isFired()){
                bullet.setBulletX(bullet.getBulletX() + BULLET_STEP_SIZE*bullet.getDirectionX());
                bullet.setBulletY(bullet.getBulletY() + BULLET_STEP_SIZE*bullet.getDirectionY());
                if(bullet.getPosition().distanceTo(closestZombieToBullet(zombieList).getPosition()) < SHOT_DEAD_RANGE){
                    bullet.setFired(false);
                    closestZombieToBullet(zombieList).setShot(true);
                }
                printToFile( bullet.getBulletX(), bullet.getBulletY());
            }


            if (!areZombiesAlive(zombieList)) {
                player.setPlayerDirectionTo(treasure.getPosition());
                player.setPlayerX(player.getPlayerX() + STEP_SIZE * player.getPlayerDirectionX());
                player.setPlayerY(player.getPlayerY() + STEP_SIZE * player.getPlayerDirectionY());
            } else if (player.getEnergy() >= 3 && closestZombie(zombieList) != null) {
                player.setPlayerDirectionTo(closestZombie(zombieList));
                player.setPlayerX(player.getPlayerX() + STEP_SIZE * player.getPlayerDirectionX());
                player.setPlayerY(player.getPlayerY() + STEP_SIZE * player.getPlayerDirectionY());
            } else if(closestSandwich(sandwichList) != null) {
                player.setPlayerDirectionTo(closestSandwich(sandwichList));
                player.setPlayerX(player.getPlayerX() + STEP_SIZE * player.getPlayerDirectionX());
                player.setPlayerY(player.getPlayerY() + STEP_SIZE * player.getPlayerDirectionY());
            }
        }

        //draw all the objects for the simulation
        background.drawBackground();
        player.drawPlayer();
        treasure.drawTreasure();
        if(bullet.isFired()) {
            bullet.drawBullet();
        }

        for(Zombie zombies:zombieList) {
            zombies.drawZombie();
        }

        for(Sandwich sandwiches:sandwichList) {
            sandwiches.drawSandwich();
        }
        font.drawString("energy: " + player.getEnergy(), ENERGY_POINT.x, ENERGY_POINT.y);

        //Update tick
        tick += 1;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();
    }
}
