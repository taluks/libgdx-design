package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;

public class CloneableImageTextButton implements CloneableResource<ImageTextButton> {

	private final ImageTextButton button;
	public CloneableImageTextButton(ImageTextButton button) {
		this.button = button;
	}

	@Override
	public ImageTextButton getActor() {
		return button;
	}

	@Override
	public ImageTextButton clone() {
		return new ImageTextButton(button.getText().toString(), button.getStyle());
	}

}
