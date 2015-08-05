package game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {

    public static void main(final String... args) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Const.WIDTH;
        config.height = Const.HEIGHT;
        config.backgroundFPS = 60;
        config.foregroundFPS = 60;
        new LwjglApplication(new TheGame(), config);
    }

}
