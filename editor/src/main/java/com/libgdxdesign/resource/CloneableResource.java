package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface CloneableResource<T extends Actor> extends Cloneable {

	public T getActor();

	T clone();
}
