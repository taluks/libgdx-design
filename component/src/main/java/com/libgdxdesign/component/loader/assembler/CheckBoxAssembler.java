package com.libgdxdesign.component.loader.assembler;


import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.model.CheckBoxData;

public class CheckBoxAssembler extends ActorAssembler<CheckBox, CheckBoxData> {

	public CheckBoxAssembler() {
	
	}

	@Override
	public CheckBox build(ProxyAssembler proxy, Skin skin, CheckBoxData data) {
		CheckBox checkBox = new CheckBox(data.text, skin, data.styleName);
		return buildParameters(proxy, skin, checkBox, data);
	}

	@Override
	public CheckBoxData assemble(ProxyAssembler proxy, Skin skin, CheckBox actor) {
		CheckBoxData data = new CheckBoxData();
		data.text = actor.getText().toString();
		data.styleName = skin.find(actor.getStyle());
		return assembleParameters(proxy, skin, data, actor);
	}


}
