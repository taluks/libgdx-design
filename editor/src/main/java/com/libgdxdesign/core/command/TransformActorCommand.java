package com.libgdxdesign.core.command;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.libgdxdesign.core.event.ActorUpdateEvent;

public class TransformActorCommand implements Command {
	
	private final Vector2 position;
	private final Vector2 size;
	private final int zIndex;
	private final Actor actor;
	private final Vector2 newPosition;
	private final Vector2 newSize;
	private final int newZIndex;
	private boolean needUpdateEvent = true;
	public TransformActorCommand(Actor actor, Vector2 newPosition, Vector2 newSize, int newZIndex) {
		this.actor = actor;
		this.newPosition = newPosition;
		this.newSize = newSize;
		this.newZIndex = newZIndex;
		
		position = new Vector2(actor.getX(), actor.getY());
		size = new Vector2(actor.getWidth(), actor.getHeight());
		zIndex = actor.getZIndex();
	}

	@Override
	public void execute(CommandExecutor commandExecutor) {
		actor.setPosition(newPosition.x, newPosition.y);
		actor.setSize(newSize.x, newSize.y);
		actor.setZIndex(newZIndex);
		commandExecutor.ui.selectActor(actor);
		if(needUpdateEvent)
			commandExecutor.eventBus.post(new ActorUpdateEvent(actor));
	}

	@Override
	public void undo(CommandExecutor commandExecutor) {		
		actor.setPosition(position.x, position.y);
		actor.setSize(size.x, size.y);
		actor.setZIndex(zIndex);
		commandExecutor.ui.unselectActor(actor);
		commandExecutor.eventBus.post(new ActorUpdateEvent(actor));
	}

	public Actor getActor() {
		return actor;
	}

	public boolean isNeedUpdateEvent() {
		return needUpdateEvent;
	}

	public void setNeedUpdateEvent(boolean needUpdateEvent) {
		this.needUpdateEvent = needUpdateEvent;
	}

}
