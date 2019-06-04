package com.libgdxdesign.core.command;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.libgdxdesign.core.event.ActorUpdateEvent;

public class ChangeActorNameCommand implements Command {

	private final Actor actor;
	private final String newName;
	private final String oldName;

	public ChangeActorNameCommand(Actor actor, String newName) {
		this.actor = actor;
		this.newName = newName;
		this.oldName = actor.getName();
	}

	@Override
	public void execute(CommandExecutor commandExecutor) {
		actor.setName(newName);
		commandExecutor.eventBus.post(new ActorUpdateEvent(actor));
	}

	@Override
	public void undo(CommandExecutor commandExecutor) {
		actor.setName(oldName);
		commandExecutor.eventBus.post(new ActorUpdateEvent(actor));
	}

}
