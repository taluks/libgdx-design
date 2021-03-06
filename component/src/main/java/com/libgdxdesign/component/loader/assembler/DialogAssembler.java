package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.libgdxdesign.component.model.ActorData;
import com.libgdxdesign.component.model.DialogData;

public class DialogAssembler extends ActorAssembler<Dialog, DialogData> {

	@Override
	public Dialog build(ProxyAssembler proxy, Skin skin, DialogData data) {
		Dialog window = new Dialog(data.title, skin, data.styleName);
		for (ActorData ad : data.children) {
			Actor actor = proxy.getBuilder(ad.getClass()).build(proxy, skin, ad);
			window.addActor(actor);
		}
		window.setModal(data.isModal);
		window.setMovable(data.isMovable);
		window.setResizable(data.isResizable);
		return buildParameters(window, data);
	}

	@Override
	public DialogData assemble(ProxyAssembler proxy, Skin skin, Dialog actor) {
		DialogData data = new DialogData();
		data.title = actor.getTitleLabel().getText().toString();
		data.styleName = skin.find(actor.getStyle());
		data.isMovable = actor.isMovable();
		data.isModal = actor.isModal();
		data.isResizable = actor.isResizable();
		Array<ActorData> children = new Array<>(ActorData.class);
		for (int i = 0; i < actor.getChildren().size; i++) {
			Actor child = actor.getChildren().get(i);
			ActorAssembler<Actor, ActorData> assembler = proxy.getAssembler(child.getClass());
			if (assembler == null) {
				Gdx.app.log(this.getClass().getSimpleName(), actor.getClass().getName() + " skipped");
				continue;
			}
			children.add(assembler.assemble(proxy, skin, child));
		}
		data.children = children.shrink();
		return assembleParameters(data, actor);
	}

}
