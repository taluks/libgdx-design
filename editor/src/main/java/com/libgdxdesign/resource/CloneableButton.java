package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class CloneableButton implements CloneableResource<Button> {

	private final Button button;
	public CloneableButton(Button button) {
		this.button = button;
	}

	@Override
	public Button getActor() {
		return button;
	}

	@Override
	public Button clone() {
		return new Button(button.getStyle());
	}

}
