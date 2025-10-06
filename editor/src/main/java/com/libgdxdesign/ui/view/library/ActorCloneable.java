package com.libgdxdesign.ui.view.library;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface ActorCloneable<T extends Actor> {
	T copyActor(T copyFrom);
}
