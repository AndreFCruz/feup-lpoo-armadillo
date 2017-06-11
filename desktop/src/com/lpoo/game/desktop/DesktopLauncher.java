package com.lpoo.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lpoo.game.Armadillo;

/**
 * Class used to launch the game on the desktop.
 */
public class DesktopLauncher {

    /**
     * Main function to launch game on desktop
     *
     * @param arg Main arguments.
     */
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Armadillo";
        config.height = 720;
        config.width = 1280;

        new LwjglApplication(new Armadillo(new NullGameServices()), config);
    }

}
