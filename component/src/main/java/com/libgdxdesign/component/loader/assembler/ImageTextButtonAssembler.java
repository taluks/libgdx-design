package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.model.ImageTextButtonData;

public class ImageTextButtonAssembler extends ActorAssembler<ImageTextButton, ImageTextButtonData> {

	@Override
	public ImageTextButton build(ProxyAssembler proxy, Skin skin, ImageTextButtonData data) {
		ImageTextButton button = new ImageTextButton(data.text, skin, data.styleName);
		return buildParameters(proxy, skin, button, data);
	}

	@Override
	public ImageTextButtonData assemble(ProxyAssembler proxy, Skin skin, ImageTextButton actor) {
		ImageTextButtonData data = new ImageTextButtonData();
		data.styleName = skin.find(actor.getStyle());
		data.text = actor.getText().toString();
		return assembleParameters(proxy, skin, data, actor);
	}

}
