import bagel.Image;
import bagel.util.Point;

public class Zombie {

    private final Image zombieImage = new Image("res/images/zombie.png");

    private boolean zombieStatus = true;
    private boolean shot = false;

    private final double x;
    private final double y;

    public Zombie(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return the position of Zombie
     */
    public Point getPosition() {
        return new Point(x, y);
    }

    /**
     * @return if the zombie is alive
     */
    public boolean getZombieStatus() {
        return zombieStatus;
    }

    /**
     * @return if the zombie has met a bullet
     */
    public boolean isShot() {
        return shot;
    }

    /**
     * @param shot set the shot status
     */
    public void setShot(boolean shot) {
        this.shot = shot;
    }

    /**
     * @param zombieStatus set zombie status
     */
    public void setZombieStatus(boolean zombieStatus) {
        this.zombieStatus = zombieStatus;
    }

    /**
     * @param player player
     * @return distance between player and zombie
     */
    public double distanceToPlayer(Player player){
        return new Point(player.getPlayerX(), player.getPlayerY()).distanceTo(getPosition());
    }

    /**
     * @param bullet the bullet
     * @return distance to the bullet from the zombie
     */
    public double distanceToBullet(Bullet bullet){
        return bullet.getPosition().distanceTo(getPosition());
    }

    /**
     * the zombie image gets rendered
     */
    public void drawZombie(){
        if(!shot) {
            zombieImage.draw(x, y);
        }
    }


}
