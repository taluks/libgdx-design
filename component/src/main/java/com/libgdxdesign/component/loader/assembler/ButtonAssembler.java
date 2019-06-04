package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.model.ButtonData;

public class ButtonAssembler extends ActorAssembler<Button, ButtonData> {

	public ButtonAssembler() {
		
	}

	@Override
	public Button build(ProxyAssembler proxy, Skin skin, ButtonData data) {
		Button button = new Button(skin, data.styleName);
		return buildParameters(button, data);
	}

	@Override
	public ButtonData assemble(ProxyAssembler proxy, Skin skin, Button actor) {
		ButtonData data = new ButtonData();
		data.styleName = skin.find(actor.getStyle());
		return assembleParameters(data, actor);
	}


}
