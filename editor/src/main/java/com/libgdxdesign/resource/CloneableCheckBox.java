package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;

public class CloneableCheckBox implements CloneableResource<CheckBox> {

	private final CheckBox checkBox;

	public CloneableCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}

	@Override
	public CheckBox getActor() {
		return checkBox;
	}

	@Override
	public CheckBox clone() {
		return new CheckBox(this.checkBox.getText().toString(), this.checkBox.getStyle());
	}

}
