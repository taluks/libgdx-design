package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.libgdxdesign.component.model.TextFieldData;

public class TextFieldAssembler extends ActorAssembler<com.badlogic.gdx.scenes.scene2d.ui.TextField, TextFieldData> {

	@Override
	public TextField build(ProxyAssembler proxy, Skin skin, TextFieldData data) {
		TextField textField = new TextField(data.text, skin, data.styleName);
		textField.setPasswordMode(data.passwordMode);
		textField.setMaxLength(data.maxLength);
		return buildParameters(proxy, skin, textField, data);
	}

	@Override
	public TextFieldData assemble(ProxyAssembler proxy, Skin skin, TextField actor) {
		TextFieldData data = new TextFieldData();
		data.text = actor.getText();
		data.styleName = skin.find(actor.getStyle());
		data.passwordMode = actor.isPasswordMode();
		data.maxLength = actor.getMaxLength();
		return assembleParameters(proxy, skin, data, actor);
	}

}
