import bagel.Image;
import bagel.util.Point;

public class Treasure {

    private final Image treasureImage = new Image("res/images/treasure.png");

    private final double x;
    private final double y;

    public Treasure(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return the position of the treasure
     */
    public Point getPosition() {
        return new Point(x, y);
    }

    /**
     * @param player the player
     * @return distance from the treasure to the player
     */
    public double distanceToPlayer(Player player){
        return new Point(player.getPlayerX(), player.getPlayerY()).distanceTo(getPosition());
    }

    /**
     * renders the image of the treasure
     */
    public void drawTreasure(){
        treasureImage.draw(x, y);
    }
}
