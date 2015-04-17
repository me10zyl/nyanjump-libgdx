package net.xicp.zyl.me.nyancat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.xicp.zyl.me.nyancat.NyancatGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Nyan Jump";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new NyancatGame(), config);
	}
}
