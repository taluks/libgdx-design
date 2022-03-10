package com.libgdxdesign;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFrame;
import com.libgdxdesign.core.util.Version;

public final class DesktopLauncher {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "LibGDX Design - " + Version.VERSION;
		new LwjglFrame(new DesignEditor(), config);
	}
}
