package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class CloneableLabel implements CloneableResource<Label> {

	
	private final Label label;
	public CloneableLabel(Label label) {
		super();
		this.label = label;
	}

	@Override
	public Label getActor() {
		return label;
	}

	@Override
	public Label clone() {
		return new Label(label.getText(), label.getStyle());
	}
	
	

}
