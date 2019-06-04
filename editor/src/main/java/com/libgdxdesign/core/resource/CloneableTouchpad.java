package com.libgdxdesign.core.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class CloneableTouchpad implements CloneableResource<Touchpad> {

	private Touchpad touchpad;
	private float deadzoneRadius;
	public CloneableTouchpad(Touchpad touchpad, float deadzoneRadius) {
		this.touchpad = touchpad;
		this.deadzoneRadius = deadzoneRadius;
	}

	@Override
	public Touchpad getActor() {
		return touchpad;
	}

	@Override
	public Touchpad clone() {
		return new Touchpad(deadzoneRadius, touchpad.getStyle());
	}

}
