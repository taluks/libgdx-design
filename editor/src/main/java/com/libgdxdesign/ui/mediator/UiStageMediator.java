package com.libgdxdesign.ui.mediator;

import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.mediator.DesignScreenMediator;
import com.libgdxdesign.core.view.DesignScreen;
import com.libgdxdesign.ui.view.UiStage;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

public class UiStageMediator extends Mediator {

	public static final String NAME = UiStageMediator.class.getSimpleName();

	public UiStageMediator() {
		super(NAME, new UiStage());
	}

	@Override
	public UiStage getViewComponent() {
		return (UiStage) super.getViewComponent();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[]{
				DesignNotification.CREATE,
				DesignNotification.RESIZE,
		};
	}

	@Override
	public void handleNotification(INotification notification) {

		switch (notification.getName()) {
			case DesignNotification.CREATE:
				DesignScreenMediator screenMediator = (DesignScreenMediator) facade.retrieveMediator(DesignScreenMediator.NAME);
				DesignScreen screen = screenMediator.getViewComponent();

				screen.addStage(getViewComponent());
				screen.getMultiplexer().addProcessor(0, getViewComponent());
				break;
			case DesignNotification.RESIZE:
				int[] size = (int[]) notification.getBody();
				getViewComponent().getDummyTarget().setSize(size[0], size[1]);
				break;
		}
	}
}