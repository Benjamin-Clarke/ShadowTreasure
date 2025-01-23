import bagel.Image;
import bagel.util.Point;

public class Player {

    private final Image playerImage = new Image("res/images/player.png");

    private double playerX;
    private double playerY;
    private int energy;

    private double playerDirectionX = 0;
    private double playerDirectionY = 0;

    public Player(double playerX, double playerY, int energy){
        this.playerX = playerX;
        this.playerY = playerY;
        this.energy = energy;
    }

    /**
     * @return position of the player
     */
    public Point getPosition() {
        return new Point(playerX, playerY);
    }

    /**
     * @return players X coordinate
     */
    public double getPlayerX() {
        return playerX;
    }

    /**
     * @param playerX players new X coordinate
     * sets the players X coordinate
     */
    public void setPlayerX(double playerX) {
        this.playerX = playerX;
    }

    /**
     * @return players Y coordinate
     */
    public double getPlayerY() {
        return playerY;
    }

    /**
     * @param playerY players new Y coordinate
     * sets the players Y coordinate
     */
    public void setPlayerY(double playerY) {
        this.playerY = playerY;
    }

    /**
     * @return players X direction
     */
    public double getPlayerDirectionX() {
        return playerDirectionX;
    }

    /**
     * @return players Y direction
     */
    public double getPlayerDirectionY() {
        return playerDirectionY;
    }

    /**
     * @return players energy level
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * @param energy sets the players energy level
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * player image gets rendered
     */
    public void drawPlayer(){
        playerImage.draw(playerX, playerY);
    }

    /**
     * Method to set the movement direction of the player
     * @param Destination new location the player will move to
     * updates the players new direction
     */

    public void setPlayerDirectionTo(Point Destination){
        double Length = new Point(playerX, playerY).distanceTo(Destination);
        playerDirectionX = (Destination.x - playerX)/Length;
        playerDirectionY = (Destination.y - playerY)/Length;
    }

}
