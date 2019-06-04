package com.libgdxdesign.core.event;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorUpdateEvent {

	private final Actor actor;

	public ActorUpdateEvent(Actor actor) {
		this.actor = actor;
	}

	public Actor getActor() {
		return actor;
	}

}
