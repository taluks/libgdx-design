package com.libgdxdesign.core.event;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorRemoveEvent {

	private final Actor actor;

	public ActorRemoveEvent(Actor actor) {
		this.actor = actor;
	}

	public Actor getActor() {
		return actor;
	}

}
