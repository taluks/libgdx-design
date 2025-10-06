package com.libgdxdesign;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFrame;
import com.libgdxdesign.core.util.Version;

public final class DesktopLauncher {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1080;
		config.height = 640;
		config.title = "LibGDX Design - " + Version.VERSION;
		new LwjglFrame(new DesignEditor(), config);
	}
}
