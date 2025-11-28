package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class CloneableImageButton implements CloneableResource<Button> {

	private ImageButton button;

	public CloneableImageButton(ImageButton button) {
		this.button = button;
	}

	@Override
	public ImageButton getActor() {
		return button;
	}

	@Override
	public ImageButton clone() {
		return new ImageButton(button.getStyle());
	}

}
