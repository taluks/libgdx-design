package com.libgdxdesign.core.command;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class MoveActorCommand implements Command {

	private final Actor actor;
	private final float oldX;
	private final float oldY;
	private final float x;
	private final float y;
	public MoveActorCommand(Actor actor, float x, float y) {
		this.actor = actor;
		this.x = x;
		this.y = y;
		this.oldX = actor.getX();
		this.oldY = actor.getY();
	}

	@Override
	public void execute(CommandExecutor commandExecutor) {
		actor.setPosition(x, y);
	}

	@Override
	public void undo(CommandExecutor commandExecutor) {
		actor.setPosition(oldX, oldY);
	}

}
