package com.lpoo.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lpoo.game.Armadillo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Armadillo";
        config.height = 450;
        config.width = 800;

        new LwjglApplication(new Armadillo(new NullGameServices()), config);
	}

}
