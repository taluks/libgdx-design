package com.libgdxdesign.view.property;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.libgdxdesign.core.event.ActorSelectEvent;
import com.libgdxdesign.core.event.ActorUpdateEvent;

public class PropertiesPanel extends Table {

	private List<AbstractPropertiesPanel<?>> propertiePanels = new ArrayList<>();
	private Table group = new Table();
	private Actor currentActor;

	public PropertiesPanel(EventBus eventBus, Skin skin) {
		setBackground(skin.getDrawable("list"));

		BaseProperties baseProperties = new BaseProperties(skin, eventBus);
		group.add(baseProperties).top().left().row();

		addProperties(baseProperties);
		addProperties(new LabelProperties(skin, eventBus));
		addProperties(new WindowProperties(skin, eventBus));

		add(group).expand().top().left().pad(4);
		pack();

		eventBus.register(this);
	}

	private AbstractPropertiesPanel<?> addProperties(AbstractPropertiesPanel<?> panel) {
		propertiePanels.add(panel);
		return panel;
	}

	@Subscribe
	private void actorSelectEvent(ActorSelectEvent event) {
		setActor(event.getActor());
	}

	@Subscribe
	private void updatePositionAndSize(ActorUpdateEvent event) {
		setActor(event.getActor());
	}

	private void setActor(Actor actor) {
		
		propertiePanels.forEach(p -> p.update(actor));
		if(currentActor != actor)
			showProperties(actor);
		currentActor = actor;
	}

	public void showProperties(Actor actor) {
		group.clear();
		propertiePanels.forEach(pp -> {
			if ((pp.getActorClass()).isAssignableFrom(actor.getClass())) {
				group.add(pp).top().left().row();
			}
		});
	}

}
