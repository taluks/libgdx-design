package com.libgdxdesign;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFrame;
import com.libgdxdesign.utils.Version;

public final class DesktopLauncher {

	public static void main(String[] args) {

		Rectangle maximumWindowBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		double width = maximumWindowBounds.getWidth();
		double height = maximumWindowBounds.getHeight();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1000;//(int) width;
		config.height = 600;//(int) height;
		config.backgroundFPS = 0;
		config.title = "LibGDX Design - " + Version.VERSION;
		new LwjglFrame(new DesignEditor(), config);
	}
}
