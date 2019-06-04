package com.libgdxdesign.core.event;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorAddEvent {

	private final Actor actor;

	public ActorAddEvent(Actor actor) {
		this.actor = actor;
	}

	public Actor getActor() {
		return actor;
	}

}
