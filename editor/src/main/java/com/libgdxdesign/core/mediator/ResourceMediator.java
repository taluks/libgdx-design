package com.libgdxdesign.core.mediator;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.UIComponent;
import com.libgdxdesign.core.controller.notification.ResourceNotification;
import com.libgdxdesign.core.model.Project;

public class ResourceMediator extends Mediator {
	public static final String NAME = ResourceMediator.class.getCanonicalName();

	private final Project project = new Project();

	public ResourceMediator() {
		super(NAME);
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] { 
				ResourceNotification.SKIN_LOADED, 
				ResourceNotification.UI_LOADED };
	}

	@Override
	public void handleNotification(INotification notification) {
		switch (notification.getName()) {
		case ResourceNotification.SKIN_LOADED:
			project.setSkin((Skin) notification.getBody());
			break;
		case ResourceNotification.UI_LOADED:
			project.setUiComponent((UIComponent) notification.getBody());
		default:
			break;
		}

	}

}
