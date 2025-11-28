package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.libgdxdesign.component.model.TextAreaData;

public class TextAreaAssembler extends ActorAssembler<TextArea, TextAreaData> {

	@Override
	public TextArea build(ProxyAssembler proxy, Skin skin, TextAreaData data) {
		TextArea textArea = new TextArea(data.text, skin, data.styleName);
		textArea.setMaxLength(data.maxLength);
		return buildParameters(proxy, skin, textArea, data);
	}

	@Override
	public TextAreaData assemble(ProxyAssembler proxy, Skin skin, TextArea actor) {
		TextAreaData data = new TextAreaData();
		data.maxLength = actor.getMaxLength();
		data.text = actor.getText();
		data.styleName = skin.find(actor.getStyle());
		return assembleParameters(proxy, skin, data, actor);
	}

}
