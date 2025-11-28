package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.libgdxdesign.component.model.TextButtonData;

public class TextButtonAssembler extends ActorAssembler<TextButton, TextButtonData> {

	public TextButtonAssembler() {
		
	}

	@Override
	public TextButton build(ProxyAssembler proxy, Skin skin, TextButtonData data) {
		TextButton button = new TextButton(data.text, skin, data.styleName);
		return buildParameters(proxy, skin, button, data);
	}

	@Override
	public TextButtonData assemble(ProxyAssembler proxy, Skin skin, TextButton actor) {
		TextButtonData data = new TextButtonData();
		data.text = actor.getLabel().getText().toString();
		data.styleName = skin.find(actor.getStyle());
		return assembleParameters(proxy, skin, data, actor);
	}

}
