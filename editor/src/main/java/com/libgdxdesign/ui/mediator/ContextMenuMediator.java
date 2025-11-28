package com.libgdxdesign.ui.mediator;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ClickInfo;
import com.libgdxdesign.ui.event.ActionEvent;
import com.libgdxdesign.ui.view.ContextMenu;
import com.libgdxdesign.ui.view.UiStage;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

public class ContextMenuMediator extends Mediator {

    public static final String NAME = ContextMenuMediator.class.getSimpleName();
    private Actor contextActor;

    public ContextMenuMediator() {
        super(NAME, new ContextMenu());
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                DesignNotification.CREATE,

                EditorNotification.ACTOR_SELECTED,
                EditorNotification.ACTOR_DESELECTED,
                EditorNotification.OPEN_GROUP_EDIT,
                EditorNotification.SHOW_CONTEXT_MENU
        };
    }

    @Override
    public void handleNotification(INotification notification) {
        UiStageMediator sceneMediator = (UiStageMediator) facade.retrieveMediator(UiStageMediator.NAME);
        UiStage stage = sceneMediator.getViewComponent();

        switch (notification.getName()) {
            case DesignNotification.CREATE -> {

                EventListener actionListener = (event) -> {
                    if (event instanceof ActionEvent<?> action) {
                        switch ((ContextMenu.Action)action.getAction()) {
                            case ADD_CELL -> facade.sendNotification(EditorNotification.TABLE_ADD_CELL, contextActor);
                            case ROW -> facade.sendNotification(EditorNotification.TABLE_ROW, contextActor);
                            case FILL -> facade.sendNotification(EditorNotification.TABLE_CELL_FILL, contextActor);
                            case EXPAND -> facade.sendNotification(EditorNotification.TABLE_CELL_EXPAND, contextActor);
                            case CLEAR_CHILDREN -> facade.sendNotification(EditorNotification.TABLE_CLEAR_CHILDREN, contextActor);
                            case RESET_CELL -> facade.sendNotification(EditorNotification.TABLE_CELL_RESET, contextActor);
                        }
                        return true;
                    }
                    return false;
                };

                getViewComponent().addListener(actionListener);
            }
            case EditorNotification.SHOW_CONTEXT_MENU -> {
                ClickInfo info = (ClickInfo) notification.getBody();
                contextActor = info.target();
                getViewComponent().rebuildFor(contextActor);
                getViewComponent().getMenu().showMenu(stage, info.x(), info.y());
            }
        }
    }

    @Override
    public ContextMenu getViewComponent() {
        return (ContextMenu) super.getViewComponent();
    }
}