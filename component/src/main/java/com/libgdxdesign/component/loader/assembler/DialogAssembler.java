package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.model.DialogData;

public class DialogAssembler extends AbstractWindowAssembler<Dialog, DialogData> {

	@Override
	public Dialog build(ProxyAssembler proxy, Skin skin, DialogData data) {
		Dialog window = new Dialog(data.title, skin, data.styleName);
		return buildParameters(proxy, skin, window, data);
	}

	@Override
	public DialogData assemble(ProxyAssembler proxy, Skin skin, Dialog actor) {
		return assembleParameters(proxy, skin, new DialogData(), actor);
	}

}
