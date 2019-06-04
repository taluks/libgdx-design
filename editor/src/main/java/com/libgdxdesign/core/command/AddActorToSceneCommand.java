package com.libgdxdesign.core.command;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.libgdxdesign.core.event.ActorAddEvent;
import com.libgdxdesign.core.event.ActorRemoveEvent;
import com.libgdxdesign.core.event.ActorUpdateEvent;

public class AddActorToSceneCommand implements Command {

	private static int counter = 1;
	private final Actor actor;
	public AddActorToSceneCommand(Actor actor) {
		this.actor = actor;
	}

	@Override
	public void execute(CommandExecutor commandExecutor) {
		actor.setName(actor.getClass().getSimpleName() + counter++);
				
		commandExecutor.scene.addActor(actor, actor.getX(), actor.getY());
		
		Vector2 tmpVector = new Vector2();
		actor.getParent().stageToLocalCoordinates(tmpVector.set(actor.getX(), actor.getY()));
		actor.setPosition(tmpVector.x, tmpVector.y);
		
		
		commandExecutor.ui.selectActor(actor);
		commandExecutor.eventBus.post(new ActorUpdateEvent(actor));
		commandExecutor.eventBus.post(new ActorAddEvent(actor));
	}

	@Override
	public void undo(CommandExecutor commandExecutor) {
		actor.remove();
		commandExecutor.ui.unselectActor(actor);
		commandExecutor.eventBus.post(new ActorUpdateEvent(actor));
		commandExecutor.eventBus.post(new ActorRemoveEvent(actor));
	}

	public Actor getActor() {
		return actor;
	}

}
