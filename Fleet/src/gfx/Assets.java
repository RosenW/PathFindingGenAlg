package gfx;

import core.Button;
import core.Game;

import java.io.IOException;

/**
 * Created by Rosen on 26-Jul-16.
 */
public class Assets {
    public static Button startButton;
    public static int startButtonX = Game.WIDTH / 2 - 100;
    public static int startButtonY = Game.HEIGHT/3;

    public static void init() throws IOException {
        startButton = new Button(startButtonX, startButtonY, ImageLoader.loadImage("/StartButtonImage.png"));
    }

}
