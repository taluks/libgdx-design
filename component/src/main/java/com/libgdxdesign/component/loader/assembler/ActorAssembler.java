package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.loader.ReflectionUtils;
import com.libgdxdesign.component.model.ActorData;

public abstract class ActorAssembler<A extends Actor, D extends ActorData> {

	public ActorAssembler() {

	}

	public abstract A build(ProxyAssembler proxy, Skin skin, D data);

	public abstract D assemble(ProxyAssembler proxy, Skin skin, A actor);

	public Class<?> getActorClass() {
		return ReflectionUtils.getGenericParameterClass(this.getClass(), 0);
	}

	public Class<?> getActorDataClass() {
		return ReflectionUtils.getGenericParameterClass(this.getClass(), 1);
	}

	public A buildParameters(A actor, D data) {
		actor.setName(data.name);
		actor.setPosition(data.x, data.y);
		actor.setSize(data.width, data.height);
		actor.setOrigin(data.originX, data.originY);
		actor.setScale(data.scaleX, data.scaleY);
		actor.setRotation(data.rotation);
		actor.setZIndex(data.z);
		actor.setVisible(data.visible);
		return actor;
	}

	public D assembleParameters(D data, A actor) {
		data.className = actor.getClass().getName();
		data.name = actor.getName();
		data.x = actor.getX();
		data.y = actor.getY();
		data.width = actor.getWidth();
		data.height = actor.getHeight();
		data.originX = actor.getOriginX();
		data.originY = actor.getOriginY();
		data.scaleX = actor.getScaleX();
		data.scaleY = actor.getScaleY();
		data.rotation = actor.getRotation();
		data.z = actor.getZIndex();
		data.visible = actor.isVisible();
		return data;
	}
}
