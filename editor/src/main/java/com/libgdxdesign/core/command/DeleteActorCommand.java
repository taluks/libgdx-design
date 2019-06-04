package com.libgdxdesign.core.command;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.libgdxdesign.core.event.ActorAddEvent;
import com.libgdxdesign.core.event.ActorRemoveEvent;
import com.libgdxdesign.core.event.ActorUpdateEvent;

public class DeleteActorCommand implements Command {

	private Actor actor;

	public DeleteActorCommand() {
	}

	@Override
	public void execute(CommandExecutor commandExecutor) {
		if (actor == null) {
			actor = commandExecutor.ui.getSelectionRectangle().getActor();
		}
		
		if (actor == null)
			return;
		actor.remove();
		commandExecutor.ui.unselectActor(actor);
		commandExecutor.eventBus.post(new ActorRemoveEvent(actor));
	}

	@Override
	public void undo(CommandExecutor commandExecutor) {
		if (actor == null)
			return;
		commandExecutor.scene.addActor(actor);
		commandExecutor.ui.selectActor(actor);
		commandExecutor.eventBus.post(new ActorUpdateEvent(actor));
		commandExecutor.eventBus.post(new ActorAddEvent(actor));
	}

}
