package com.libgdxdesign.core.mediator;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.UIComponent;
import com.libgdxdesign.core.controller.notification.ResourceNotification;
import com.libgdxdesign.core.model.Project;
import com.libgdxdesign.core.proxy.ProjectProxy;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

public class ResourceMediator extends Mediator {

    public static final String NAME = ResourceMediator.class.getCanonicalName();

    public ResourceMediator() {
        super(NAME);
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                ResourceNotification.SKIN_LOADED,
                ResourceNotification.UI_LOADED
        };
    }

    @Override
    public void handleNotification(INotification notification) {
        ProjectProxy projectProxy = (ProjectProxy) facade.retrieveProxy(ProjectProxy.NAME);
        Project project = projectProxy.getProject();
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