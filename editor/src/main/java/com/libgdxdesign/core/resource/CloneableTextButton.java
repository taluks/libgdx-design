package com.libgdxdesign.core.resource;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class CloneableTextButton implements CloneableResource<TextButton> {

	private TextButton button;

	public CloneableTextButton(TextButton button) {
		this.button = button;
	}

	@Override
	public TextButton getActor() {
		return button;
	}

	@Override
	public TextButton clone() {
		return new TextButton(button.getText().toString(), button.getStyle());
	}

}
