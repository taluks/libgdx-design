package com.libgdxdesign.core.mediator;

import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.view.DesignScreen;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

public class DesignScreenMediator extends Mediator {
    public static final String NAME = DesignScreenMediator.class.getSimpleName();

    public DesignScreenMediator() {
        super(NAME, new DesignScreen());
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                DesignNotification.CREATE,
                DesignNotification.RENDER,
                DesignNotification.RESIZE,
                DesignNotification.RESUME,
                DesignNotification.PAUSE,
                DesignNotification.DISPOSE
        };
    }

    @Override
    public DesignScreen getViewComponent() {
        return (DesignScreen) viewComponent;
    }

    @Override
    public void handleNotification(INotification notification) {
        switch (notification.getName()) {
            case DesignNotification.CREATE:
                getViewComponent().show();
                break;
            case DesignNotification.RENDER:
                getViewComponent().render((float) notification.getBody());
                break;
            case DesignNotification.RESIZE:
                int[] size = (int[]) notification.getBody();
                getViewComponent().resize(size[0], size[1]);
                break;
        }
    }
}