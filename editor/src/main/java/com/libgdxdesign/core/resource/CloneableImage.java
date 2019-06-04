package com.libgdxdesign.core.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CloneableImage implements CloneableResource<Image> {

	private Image image;
	public CloneableImage(Image image) {
		this.image = image;
	}

	@Override
	public Image getActor() {
		return image;
	}

	@Override
	public Image clone() {
		return new Image(image.getDrawable());
	}

}
