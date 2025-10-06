package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.libgdxdesign.resource.ResourceWrapper;
import com.libgdxdesign.scene.mediator.SceneMediator;

public class AddActorToSceneCommand extends RevertibleCommand{

	private static int counter = 1;

	@Override
	public void doAction() {
		ResourceWrapper wrapper = (ResourceWrapper) body;
		Actor actor = wrapper.getActor();

		actor.setName(actor.getClass().getSimpleName() + counter++);
		SceneMediator scene = (SceneMediator)facade.retrieveMediator(SceneMediator.NAME);
		scene.getViewComponent().addActor(actor);

		Vector2 tmpVector = new Vector2();
		actor.getParent().stageToLocalCoordinates(tmpVector.set(actor.getX(), actor.getY()));
		actor.setPosition(tmpVector.x, tmpVector.y);
	}

	@Override
	public void undoAction() {
		ResourceWrapper wrapper = (ResourceWrapper) body;
		wrapper.getActor().remove();
	}
}
