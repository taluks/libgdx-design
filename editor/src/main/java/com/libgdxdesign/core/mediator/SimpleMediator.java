package com.libgdxdesign.core.mediator;

import org.puremvc.java.patterns.mediator.Mediator;

import com.libgdxdesign.core.controller.notification.DesignNotification;

public class SimpleMediator extends Mediator {

	
	public SimpleMediator() {
		super(SimpleMediator.class.getCanonicalName(), null);
	}
	
	@Override
	public String[] listNotificationInterests() {
		return new String[] {
				DesignNotification.CREATE,
				DesignNotification.RENDER,
				DesignNotification.RESIZE,
				DesignNotification.RESUME,
				DesignNotification.PAUSE,
				DesignNotification.DISPOSE
				};
	}

}
