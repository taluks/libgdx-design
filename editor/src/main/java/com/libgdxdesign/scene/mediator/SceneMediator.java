package com.libgdxdesign.scene.mediator;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.mediator.DesignScreenMediator;
import com.libgdxdesign.core.view.DesignScreen;
import com.libgdxdesign.scene.view.Scene;
import com.libgdxdesign.scene.view.SceneController;

public class SceneMediator extends Mediator {
	public static final String NAME = SceneMediator.class.getSimpleName();

	public SceneMediator() {
		super(NAME, new Scene());
	}

	@Override
	public Scene getViewComponent() {
		return (Scene) super.getViewComponent();
	}

	@Override
	public String[] listNotificationInterests() {
		return new String[] { DesignNotification.CREATE };
	}

	@Override
	public void handleNotification(INotification notification) {
		DesignScreenMediator screenMediator = (DesignScreenMediator) facade.retrieveMediator(DesignScreenMediator.NAME);
		DesignScreen screen = screenMediator.getViewComponent();
		
		screen.addStage(getViewComponent());
		screen.getMultiplexer().addProcessor(getViewComponent());
		//screen.getMultiplexer().addProcessor(new SceneController(getViewComponent()));
	}

}
