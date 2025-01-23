import bagel.Image;
import bagel.util.Point;

public class Sandwich {

    private final Image sandwichImage = new Image("res/images/sandwich.png");

    private final double x;
    private final double y;

    private boolean sandwichEnergy = true;

    public Sandwich(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return the position of the sandwich
     */
    public Point getPosition() {
        return new Point(x, y);
    }

    /**
     * @param sandwichEnergy sets sandwich energy
     */
    public void setSandwichEnergy(boolean sandwichEnergy) {
        this.sandwichEnergy = sandwichEnergy;
    }

    /**
     * @return sandwich energy
     */
    public boolean getSandwichEnergy() {
        return sandwichEnergy;
    }

    /**
     * @param player the player
     * @return distance between the player and the sandwich
     */
    public double distanceToPlayer(Player player){
        return new Point(player.getPlayerX(), player.getPlayerY()).distanceTo(getPosition());
    }

    /**
     * Method which allows for the removal of the sandwich when it has already been "eaten"
     * renders the sandwich
     */
    public void drawSandwich(){
        if(sandwichEnergy){
            sandwichImage.draw(x, y);
        }
    }

}


