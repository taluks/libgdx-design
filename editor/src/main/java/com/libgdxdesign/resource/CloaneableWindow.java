package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class CloaneableWindow implements CloneableResource<Window> {

	private Window window;

	public CloaneableWindow(Window window) {
		this.window = window;
	}

	@Override
	public Window getActor() {
		return window;
	}

	@Override
	public Window clone() {
		return new Window(window.getTitleLabel().getText().toString(), window.getStyle());
	}

}
