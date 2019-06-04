package com.libgdxdesign.core.resource;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.google.common.eventbus.EventBus;
import com.libgdxdesign.core.command.AddActorToSceneCommand;

public class ResourceItem extends HorizontalGroup {

	private final DragAndDrop dragAndDrop;
	private final VerticalGroup verticalGroup;
	private final CloneableResource<? extends Actor> resource;
	private final Label nameLabel;
	private final EventBus eventBus;

	public ResourceItem(EventBus eventBus, CloneableResource<? extends Actor> resource, Skin skin, String name) {
		this.eventBus = eventBus;
		this.resource = resource;
		dragAndDrop = new DragAndDrop();

		nameLabel = new Label(name, skin);
		nameLabel.setWidth(100);
		nameLabel.setEllipsis(true);
		nameLabel.setTouchable(Touchable.disabled);

		Table table = new Table();
		table.setFillParent(true);
		table.add(resource.getActor()).maxSize(100).expand().center().row();
		table.add(nameLabel).maxWidth(100);
		table.setTouchable(Touchable.childrenOnly);

		Group group = new Group();
		group.setTouchable(Touchable.childrenOnly);
		group.setSize(100, 100);
		group.addActor(table);

		verticalGroup = new VerticalGroup();
		verticalGroup.setTouchable(Touchable.childrenOnly);
		verticalGroup.addActor(group);
		addActor(verticalGroup);
	}

	public void setDropTarget(Group group) {
		dragAndDrop.setDragActorPosition(-this.getWidth() / 2f, this.getHeight() / 2f);
		dragAndDrop.addSource(new DragAndDrop.Source(this) {
			@Override
			public Payload dragStart(InputEvent event, float x, float y, int pointer) {
				Actor dragActor = resource.clone();
				DragAndDrop.Payload payload = new DragAndDrop.Payload();
				payload.setDragActor(dragActor);
				payload.setInvalidDragActor(null);
				return payload;
			}
		});

		dragAndDrop.addTarget(new DragAndDrop.Target(group) {

			@Override
			public void drop(Source source, Payload payload, float x, float y, int pointer) {
				Actor actor = payload.getDragActor();
				if(actor instanceof Group) {
					Group group = (Group) actor;
					for(Actor child: group.getChildren().items) {
						if(child != null)
							child.setTouchable(Touchable.disabled);
					}
				}
				//actor.setTouchable(Touchable.disabled);
				eventBus.post(new AddActorToSceneCommand(actor));
			}

			@Override
			public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
				return true;
			}
		});
	}

	public CloneableResource<? extends Actor> getResource() {
		return resource;
	}

}
