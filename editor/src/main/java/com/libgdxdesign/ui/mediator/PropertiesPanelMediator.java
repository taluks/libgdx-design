package com.libgdxdesign.ui.mediator;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.*;
import com.libgdxdesign.ui.event.ActionEvent;
import com.libgdxdesign.ui.view.TabView;
import com.libgdxdesign.ui.view.UiStage;
import com.libgdxdesign.ui.view.property.AbstractPropertiesPanel;
import com.libgdxdesign.ui.view.property.PropertiesPanel;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import java.util.List;

public class PropertiesPanelMediator extends Mediator {
    public static final String NAME = PropertiesPanelMediator.class.getSimpleName();

    private Actor currentActor;

    public PropertiesPanelMediator() {
        super(NAME, new PropertiesPanel());
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                DesignNotification.CREATE,

                EditorNotification.ACTOR_SELECTED,
                EditorNotification.ACTOR_DESELECTED,
                EditorNotification.ACTOR_MOVED,
                EditorNotification.OPEN_GROUP_EDIT
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleNotification(INotification notification) {
        switch (notification.getName()) {
            case DesignNotification.CREATE -> {
                UiStageMediator sceneMediator = (UiStageMediator) facade.retrieveMediator(UiStageMediator.NAME);
                UiStage stage = sceneMediator.getViewComponent();

                EventListener actionListener = (event) -> {
                    if (event instanceof ActionEvent<?> action) {
                        switch ((AbstractPropertiesPanel.Action)action.getAction()) {
                            case X -> facade.sendNotification(EditorNotification.UPDATE_ACTOR_POSITION, new ActorPosition(currentActor, ActorPosition.Axis.X, action.getData()));
                            case Y -> facade.sendNotification(EditorNotification.UPDATE_ACTOR_POSITION, new ActorPosition(currentActor, ActorPosition.Axis.Y, action.getData()));
                            case Z -> facade.sendNotification(EditorNotification.UPDATE_ACTOR_POSITION, new ActorPosition(currentActor, ActorPosition.Axis.Z, action.getData()));
                            case NAME -> facade.sendNotification(EditorNotification.UPDATE_ACTOR_NAME, new ActorName(currentActor, (String) action.getData()));
                            case WIDTH -> facade.sendNotification(EditorNotification.UPDATE_ACTOR_WIDTH, new ActorSize(currentActor, (float) action.getData()));
                            case HEIGHT -> facade.sendNotification(EditorNotification.UPDATE_ACTOR_HEIGHT, new ActorSize(currentActor, (float) action.getData()));

                            case CENTER -> facade.sendNotification(EditorNotification.TABLE_CELL_ALIGN, new CellAlign(currentActor, CellAlign.Align.CENTER));
                            case LEFT -> facade.sendNotification(EditorNotification.TABLE_CELL_ALIGN, new CellAlign(currentActor, CellAlign.Align.LEFT));
                            case RIGHT -> facade.sendNotification(EditorNotification.TABLE_CELL_ALIGN, new CellAlign(currentActor, CellAlign.Align.RIGHT));
                            case TOP -> facade.sendNotification(EditorNotification.TABLE_CELL_ALIGN, new CellAlign(currentActor, CellAlign.Align.TOP));
                            case BOTTOM -> facade.sendNotification(EditorNotification.TABLE_CELL_ALIGN, new CellAlign(currentActor, CellAlign.Align.BOTTOM));

                            case PAD_LEFT -> facade.sendNotification(EditorNotification.TABLE_CELL_PAD, new CellPad(currentActor, CellPad.Type.LEFT, (float) action.getData()));
                            case PAD_RIGHT -> facade.sendNotification(EditorNotification.TABLE_CELL_PAD, new CellPad(currentActor, CellPad.Type.RIGHT, (float) action.getData()));
                            case PAD_TOP -> facade.sendNotification(EditorNotification.TABLE_CELL_PAD, new CellPad(currentActor, CellPad.Type.TOP, (float) action.getData()));
                            case PAD_BOTTOM -> facade.sendNotification(EditorNotification.TABLE_CELL_PAD, new CellPad(currentActor, CellPad.Type.BOTTOM, (float) action.getData()));

                            case WINDOW_TITLE -> facade.sendNotification(EditorNotification.UPDATE_WINDOW_TITLE, new WindowTitle(currentActor, (String)action.getData()));
                            case LABEL_TEXT -> facade.sendNotification(EditorNotification.UPDATE_LABEL_TEXT, new LabelText(currentActor, (String)action.getData()));
                        }
                        return true;
                    }
                    return false;
                };
                stage.getRightTabbedPane().add(new TabView("Properties", getViewComponent()));
                getViewComponent().addListener(actionListener);
            }
            case EditorNotification.ACTOR_SELECTED, EditorNotification.ACTOR_MOVED -> {
                List<ActorWrapper> wrapper = (List<ActorWrapper>) notification.getBody();
                currentActor = wrapper.get(0).getActor();
                getViewComponent().showProperties(wrapper.get(0).getActor());
            }
            case EditorNotification.ACTOR_DESELECTED -> {
                getViewComponent().clearProperties();
            }
            case EditorNotification.OPEN_GROUP_EDIT -> {
                ActorWrapper wrapper = (ActorWrapper) notification.getBody();
                getViewComponent().showProperties(wrapper.getActor());
            }
        }
    }

    @Override
    public PropertiesPanel getViewComponent() {
        return (PropertiesPanel) super.getViewComponent();
    }
}
