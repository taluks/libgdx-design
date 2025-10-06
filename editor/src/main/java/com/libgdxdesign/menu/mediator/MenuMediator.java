package com.libgdxdesign.menu.mediator;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.menu.view.TopMenu;
import com.libgdxdesign.ui.mediator.UiStageMediator;
import com.libgdxdesign.ui.view.UiStage;

public class MenuMediator extends Mediator {
	public static final String NAME = MenuMediator.class.getSimpleName();

	public MenuMediator() {
		super(NAME, new TopMenu());
	}

	@Override
	public TopMenu getViewComponent() {
		return (TopMenu) super.getViewComponent();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] { DesignNotification.CREATE };
	}

	@Override
	public void handleNotification(INotification notification) {
		UiStageMediator sceneMediator = (UiStageMediator) facade.retrieveMediator(UiStageMediator.NAME);
		UiStage stage = sceneMediator.getViewComponent();

		stage.getFullScreenTable().add(getViewComponent()).growX();
	}

}
