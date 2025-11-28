package com.libgdxdesign.ui.mediator;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorWrapper;
import com.libgdxdesign.ui.view.Breadcrumbs;
import com.libgdxdesign.ui.view.UiStage;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

public class BreadcrumbsMediator extends Mediator {

    public static final String NAME = BreadcrumbsMediator.class.getSimpleName();

    public BreadcrumbsMediator() {
        super(NAME, new Breadcrumbs());
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                DesignNotification.CREATE,

                EditorNotification.OPEN_GROUP_EDIT,
                EditorNotification.CLOSE_GROUP_EDIT
        };
    }

    @Override
    public void handleNotification(INotification notification) {
        UiStageMediator sceneMediator = (UiStageMediator) facade.retrieveMediator(UiStageMediator.NAME);
        UiStage stage = sceneMediator.getViewComponent();

        switch (notification.getName()) {
            case DesignNotification.CREATE -> {
                stage.getLeftTable().add(getViewComponent()).height(24).bottom().expandX().fillX();
            }
            case EditorNotification.OPEN_GROUP_EDIT -> {
                ActorWrapper actorWrapper = (ActorWrapper) notification.getBody();
                getViewComponent().setActor(actorWrapper.getActor());
            }
            case EditorNotification.CLOSE_GROUP_EDIT -> {
                Group group = (Group) notification.getBody();
                getViewComponent().setActor(group.getParent());
            }
        }
    }

    @Override
    public Breadcrumbs getViewComponent() {
        return (Breadcrumbs) super.getViewComponent();
    }

}
