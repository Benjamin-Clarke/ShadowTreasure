import bagel.Image;

public class Background {

    private final Image backgroundImage = new Image("res/images/background.png");

    /**
     * renders the background from top left coordinate
     */
    public void drawBackground(){
        backgroundImage.drawFromTopLeft(0,0);
    }
}
