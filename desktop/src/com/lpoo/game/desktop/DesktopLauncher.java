package com.lpoo.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lpoo.game.NullGameServices;
import com.lpoo.game.Spheral;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Spheral - The Game";
        config.height = 450;
        config.width = 800;

        new LwjglApplication(new Spheral(new NullGameServices()), config);
	}

}
