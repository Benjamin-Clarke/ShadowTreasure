import bagel.Image;
import bagel.util.Point;

public class Bullet {

    private final Image bulletImage = new Image("res/images/shot.png");

    private double bulletX, bulletY;
    private boolean fired = false;

    private double directionX = 0;
    private double directionY = 0;

    public Bullet(double bulletX, double bulletY){
        this.bulletX = bulletX;
        this.bulletY = bulletY;
    }

    /**
     * renders the bullet image
     */
    public void drawBullet() {
        bulletImage.draw(bulletX, bulletY);
    }

    /**
     * @return position of the bullet
     */
    public Point getPosition() {
        return new Point(bulletX, bulletY);
    }

    /**
     * @return if the bullet has been fired
     */
    public boolean isFired() {
        return fired;
    }

    /**
     * @param fired set fired attribute
     */
    public void setFired(boolean fired) {
        this.fired = fired;
    }

    /**
     * @return bullet X position
     */
    public double getBulletX() {
        return bulletX;
    }
    /**
     * @return bullet Y position
     */
    public double getBulletY() {
        return bulletY;
    }

    /**
     * @param bulletX sets the bullet X position
     */
    public void setBulletX(double bulletX) {
        this.bulletX = bulletX;
    }

    /**
     * @param bulletY sets the bullet Y position
     */
    public void setBulletY(double bulletY) {
        this.bulletY = bulletY;
    }

    /**
     * @return bullet X direction
     */
    public double getDirectionX() {
        return directionX;
    }

    /**
     * @return bullet Y direction
     */
    public double getDirectionY() {
        return directionY;
    }

    /**
     * @param Destination destination the bullet will travel to
     *                    updates the x and y direction of the bullet
     */
    public void setBulletDirectionTo(Point Destination){
        double Length = new Point(bulletX, bulletY).distanceTo(Destination);
        directionX = (Destination.x - bulletX)/Length;
        directionY = (Destination.y - bulletY)/Length;
    }

}
