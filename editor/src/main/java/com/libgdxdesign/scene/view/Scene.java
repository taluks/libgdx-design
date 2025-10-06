package com.libgdxdesign.scene.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashSet;
import java.util.Set;

public class Scene extends Stage {

	private final Set<Actor> actors = new HashSet<>();

	public Scene() {
		super(new ScreenViewport());

		addActor(new GridRenderer(this));
		//addActor(new RulersRenderer(this));

		getRoot().setTouchable(Touchable.childrenOnly);
		getRoot().setName("root");

		this.addCaptureListener(new InputListener() {
			private final Vector2 lastTouchPosition = new Vector2();
			private final Vector2 originalActorPosition = new Vector2();

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Actor target = event.getTarget();
				// Actor sceneActor = getRoot().hit(x, y, true);
				if (actors.contains(target)) {
					//eventBus.post(new ActorSelectEvent(target));
					lastTouchPosition.set(x, y);
					originalActorPosition.set(target.getX(), target.getY());
					return true;
				}
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				lastTouchPosition.setZero();
				originalActorPosition.setZero();
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				event.getTarget().setPosition(originalActorPosition.x + x - lastTouchPosition.x,
						originalActorPosition.y + y - lastTouchPosition.y);
				Actor target = event.getTarget();
				if (actors.contains(target)) {
					//eventBus.post(new ActorSelectEvent(target));
				}
				super.touchDragged(event, x, y, pointer);
			}

		});
	}

}
