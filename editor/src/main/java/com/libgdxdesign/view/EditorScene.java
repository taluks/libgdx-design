package com.libgdxdesign.view;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.libgdxdesign.core.event.ActorAddEvent;
import com.libgdxdesign.core.event.ActorSelectEvent;
import com.libgdxdesign.model.Project;
import com.libgdxdesign.scene.GridView;
import com.libgdxdesign.utils.Assets;

@Singleton
public class EditorScene extends Stage {

	private EventBus eventBus;
	private GridView grid;
	private Set<Actor> actors;

	@Inject
	public EditorScene(EventBus eventBus, Assets assets) {
		super(new ScreenViewport());
		this.eventBus = eventBus;

		grid = new GridView(assets.getSkin());
		grid.setTouchable(Touchable.disabled);
		addActor(grid);

		actors = new HashSet<>();

		getRoot().setTouchable(Touchable.childrenOnly);
		getRoot().setName("root");

		this.addCaptureListener(new InputListener() {
			private Vector2 lastTouchPosition = new Vector2();
			private Vector2 originalActorPosition = new Vector2();

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Actor target = event.getTarget();
				// Actor sceneActor = getRoot().hit(x, y, true);
				if (actors.contains(target)) {
					eventBus.post(new ActorSelectEvent(target));
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
					eventBus.post(new ActorSelectEvent(target));
				}
				super.touchDragged(event, x, y, pointer);
			}

		});

		eventBus.register(this);
	}

	// ============================
	public void setProject(Project project) {
		ObjectMap<String, Actor> componentActors = project.builUiComponent();

		for (Actor actor : componentActors.values()) {
			getRoot().addActor(actor);
			
			Array<Actor> children = findActors(actor);
			for (Actor child : children) {
				actors.add(child);
				eventBus.post(new ActorAddEvent(child));
				if (child instanceof Group) {
					Group group = (Group) child;
					group.getChildren().forEach(c -> {
						if (c != null && c.getName() == null)
							c.setTouchable(Touchable.disabled);
					});
				}
			}			
			
		}
	}

	private Array<Actor> findActors(Actor actor) {
		Array<Actor> actors = new Array<>();
		if (actor.getName() != null) {
			actors.add(actor);
			if (actor instanceof Group) {
				Group group = (Group) actor;
				group.getChildren().forEach(a -> {
					actors.addAll(findActors(a));
				});
			}
		}
		return actors;
	}
	// ============================

	public Array<Actor> getActors() {
		Array<Actor> children = getRoot().getChildren();
		Array<Actor> temp = new Array<>();
		for (Actor child : children) {
			if (actors.contains(child)) {
				temp.add(child);
			}
		}
		return temp;
	}

	public Project assembleToProject(Project project) {
		Array<Actor> temp = getActors();
		if (temp.size == 1) {
			project.getUiComponent().assemble(project.getSkin(), temp.first());
		} else {
			project.getUiComponent().assemble(project.getSkin(), temp);
		}

		return project;
	}

	// ============================
	public void addActor(Actor actor, float x, float y) {
		Actor sceneActor = getRoot().hit(x, y, false);
		if (sceneActor instanceof Window) {
			addActor((Group) sceneActor, actor);
		} else {
			addActor(getRoot(), actor);
		}
	}

	public void addActor(Group parent, Actor actor) {
		parent.addActor(actor);
		actors.add(actor);
	}

}
