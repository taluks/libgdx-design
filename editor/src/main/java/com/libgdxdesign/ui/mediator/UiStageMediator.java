package com.libgdxdesign.ui.mediator;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter;
import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.mediator.DesignScreenMediator;
import com.libgdxdesign.core.model.ActorWrapper;
import com.libgdxdesign.core.view.DesignScreen;
import com.libgdxdesign.ui.view.TransformRectangle;
import com.libgdxdesign.ui.view.UiStage;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import java.util.ArrayList;
import java.util.List;

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

                EditorNotification.ACTOR_SELECTED,
                EditorNotification.ACTOR_DESELECTED,
                EditorNotification.ACTOR_TRANSFORM_UPDATED,
                EditorNotification.ACTOR_UPDATED
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleNotification(INotification notification) {
        switch (notification.getName()) {
            case DesignNotification.CREATE -> {
                DesignScreenMediator screenMediator = (DesignScreenMediator) facade.retrieveMediator(DesignScreenMediator.NAME);
                DesignScreen screen = screenMediator.getViewComponent();

                screen.addStage(getViewComponent());
                screen.getMultiplexer().addProcessor(0, getViewComponent());

                getViewComponent().getRightTable().setBackground(VisUI.getSkin().getDrawable("window-bg"));

                getViewComponent().getRightTabbedPane().addListener(new TabbedPaneAdapter() {
                    @Override
                    public void switchedTab(Tab tab) {
                        Table rightTable = getViewComponent().getRightTable();
                        rightTable.clearChildren();
                        rightTable.add(tab.getPane().getTabsPane()).growX().row();
                        rightTable.add(tab.getContentTable()).expand().fill();
                    }
                });

                TransformRectangle transformRectangle = getViewComponent().getSelectionRectangle();
                transformRectangle.setListener((actors, newX, newY, newWidth, newHeight) -> {
                    TransformRectangle transformRect = getViewComponent().getSelectionRectangle();
                    facade.sendNotification(EditorNotification.TRANSFORM_ACTORS, transformRect.getTransformData());
                });
                transformRectangle.clear();
                transformRectangle.setVisible(false);

                getViewComponent().addListener(new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        if (keycode == Input.Keys.FORWARD_DEL) {
                            if (!transformRectangle.getActors().isEmpty())
                                facade.sendNotification(EditorNotification.REMOVE_ACTOR, transformRectangle.getActors());
                            return true;
                        }
                        return false;
                    }
                });
            }
            case DesignNotification.RESIZE -> {
                int[] size = (int[]) notification.getBody();
                getViewComponent().getDummyTarget().setSize(size[0], size[1]);
            }
            case EditorNotification.ACTOR_SELECTED -> {
                List<ActorWrapper> actors = (List<ActorWrapper>) notification.getBody();
                TransformRectangle transformRectangle = getViewComponent().getSelectionRectangle();
                transformRectangle.clear();
                transformRectangle.setVisible(true);
                transformRectangle.setActors(new ArrayList<>(actors));
            }
            case EditorNotification.ACTOR_DESELECTED -> {
                TransformRectangle transformRectangle = getViewComponent().getSelectionRectangle();
                transformRectangle.clear();
                transformRectangle.setVisible(false);
            }
            case EditorNotification.ACTOR_TRANSFORM_UPDATED -> {
                List<ActorWrapper> actors = (List<ActorWrapper>) notification.getBody();
                getViewComponent().getSelectionRectangle().update();
            }
            case EditorNotification.ACTOR_UPDATED -> getViewComponent().getSelectionRectangle().update();
        }
    }
}