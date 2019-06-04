package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.libgdxdesign.component.model.ImageData;

public class ImageAssembler extends ActorAssembler<Image, ImageData> {

	public ImageAssembler() {
		
	}

	@Override
	public Image build(ProxyAssembler proxy, Skin skin, ImageData data) {
		Image image = new Image(skin, data.drawableName);
		return buildParameters(image, data);
	}

	@Override
	public ImageData assemble(ProxyAssembler proxy, Skin skin, Image actor) {
		ImageData data = new ImageData();
		data.drawableName = ((BaseDrawable) actor.getDrawable()).getName();
		return assembleParameters(data, actor);
	}

}
