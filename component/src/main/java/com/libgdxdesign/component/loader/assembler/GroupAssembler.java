package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.model.ActorData;
import com.libgdxdesign.component.model.GroupData;

public class GroupAssembler extends ActorAssembler<Group, GroupData> {

	public GroupAssembler() {

	}

	@Override
	public Group build(ProxyAssembler proxy, Skin skin, GroupData data) {
		Group group = new Group();
		for (ActorData child : data.children) {
			Actor actor = proxy.getBuilder(child.getClass()).build(proxy, skin, child);
			group.addActor(actor);
		}
		return buildParameters(group, data);
	}

	@Override
	public GroupData assemble(ProxyAssembler proxy, Skin skin, Group actor) {
		GroupData data = new GroupData();
		data.children = new ActorData[actor.getChildren().size];
		for (int i = 0; i < actor.getChildren().size; i++) {
			Actor child = actor.getChildren().get(i);
			data.children[i] = proxy.getAssembler(child.getClass()).assemble(proxy, skin, child);
		}
		return assembleParameters(data, actor);
	}

}
