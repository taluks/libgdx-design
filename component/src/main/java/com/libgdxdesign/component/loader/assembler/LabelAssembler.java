package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.model.LabelData;

public class LabelAssembler extends ActorAssembler<Label, LabelData> {

	public LabelAssembler() {

	}

	@Override
	public Label build(ProxyAssembler proxy, Skin skin, LabelData data) {
		Label label = new Label(data.text, skin, data.styleName);
		label.setAlignment(data.alignment);
		return buildParameters(proxy, skin, label, data);
	}

	@Override
	public LabelData assemble(ProxyAssembler proxy, Skin skin, Label actor) {
		LabelData data = new LabelData();
		data.alignment = actor.getLabelAlign();
		data.text = actor.getText().toString();
		data.styleName = skin.find(actor.getStyle());
		return assembleParameters(proxy, skin, data, actor);
	}

}
