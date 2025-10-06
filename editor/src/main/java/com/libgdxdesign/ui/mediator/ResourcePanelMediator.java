package com.libgdxdesign.ui.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.controller.notification.ResourceNotification;
import com.libgdxdesign.resource.CloneableResource;
import com.libgdxdesign.resource.ResourceItem;
import com.libgdxdesign.resource.parser.ResourceParser;
import com.libgdxdesign.resource.parser.SkinParser;
import com.libgdxdesign.ui.view.UiStage;
import com.libgdxdesign.ui.view.library.ResourcePanel;

public class ResourcePanelMediator extends Mediator {
	public static final String NAME = ResourcePanelMediator.class.getSimpleName();

	public ResourcePanelMediator() {
		super(NAME, new ResourcePanel());
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[]{
				DesignNotification.CREATE,
				ResourceNotification.SKIN_LOADED};
	}

	@Override
	public void handleNotification(INotification notification) {
		UiStageMediator sceneMediator = (UiStageMediator) facade.retrieveMediator(UiStageMediator.NAME);
		UiStage stage = sceneMediator.getViewComponent();

		switch (notification.getName()) {
			case DesignNotification.CREATE:

				stage.getFullScreenTable().row();
				stage.getFullScreenTable().add(getViewComponent()).right().top().growY();
				break;
			case ResourceNotification.SKIN_LOADED:
				Skin skin = (Skin) notification.getBody();
				SkinParser skinParser = new SkinParser();
				for (ResourceParser parser : skinParser.getParsers()) {
					List<ResourceItem> items = new ArrayList<>();
					Map<String, CloneableResource<?>> parsers = parser.getResources(skin);
					for (Map.Entry<String, CloneableResource<?>> entry : parsers.entrySet()) {
						String key = entry.getKey();
						CloneableResource<?> value = entry.getValue();
						ResourceItem resourceItem = new ResourceItem(facade, value, skin, key);
						resourceItem.setDropTarget(stage.getDummyTarget());
						items.add(resourceItem);
					}
					getViewComponent().addItems(items);
				}
				getViewComponent().expandAll();
				break;
			default:
				break;
		}

	}

	@Override
	public ResourcePanel getViewComponent() {
		return (ResourcePanel) super.getViewComponent();
	}

}
