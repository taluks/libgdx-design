package com.libgdxdesign.core.event;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorSelectEvent {

	private final Actor actor;

	public ActorSelectEvent(Actor actor) {
		this.actor = actor;
	}

	public Actor getActor() {
		return actor;
	}

}
