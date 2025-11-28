package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.model.SelectBoxData;

public class SelectBoxAssembler extends ActorAssembler<SelectBox<?>, SelectBoxData> {

	public SelectBoxAssembler() {

	}

	@Override
	public SelectBox<?> build(ProxyAssembler proxy, Skin skin, SelectBoxData data) {
		SelectBox<?> selectBox = new SelectBox<>(skin, data.styleName);
		return buildParameters(proxy, skin, selectBox, data);
	}

	@Override
	public SelectBoxData assemble(ProxyAssembler proxy, Skin skin, SelectBox<?> actor) {
		SelectBoxData data = new SelectBoxData();
		data.styleName = skin.find(actor.getStyle());
		return assembleParameters(proxy, skin, data, actor);
	}

}
