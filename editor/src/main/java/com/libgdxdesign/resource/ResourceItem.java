package com.libgdxdesign.resource;

import org.puremvc.java.interfaces.IFacade;
import org.puremvc.java.patterns.facade.Facade;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.libgdxdesign.core.controller.notification.ResourceNotification;
import com.libgdxdesign.core.controller.notification.SceneNotification;

public class ResourceItem extends Table {

	private final DragAndDrop dragAndDrop;
	private final CloneableResource<? extends Actor> resource;
	private final IFacade facade;

	public ResourceItem(IFacade facade, CloneableResource<? extends Actor> resource, Skin skin, String name) {
		this.facade = facade;
		this.resource = resource;
		dragAndDrop = new DragAndDrop();

		Label nameLabel = new Label(name, skin);
		nameLabel.setWidth(100);
		nameLabel.setEllipsis(true);
		//nameLabel.setTouchable(Touchable.disabled);
		add(nameLabel).expandX().left();
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
				ResourceWrapper wrapper = new ResourceWrapper(actor);
				facade.sendNotification(SceneNotification.ADD_ACTOR, wrapper);
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
