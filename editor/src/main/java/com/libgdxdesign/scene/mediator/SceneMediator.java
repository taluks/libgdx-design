package com.libgdxdesign.scene.mediator;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.mediator.DesignScreenMediator;
import com.libgdxdesign.core.model.ActorWrapper;
import com.libgdxdesign.core.model.ClickInfo;
import com.libgdxdesign.core.view.DesignScreen;
import com.libgdxdesign.scene.view.Scene;
import com.libgdxdesign.component.loader.TableUtils;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        return new String[]{
                DesignNotification.CREATE,

                EditorNotification.OPEN_GROUP_EDIT,
                EditorNotification.CLOSE_GROUP_EDIT,
                EditorNotification.ACTOR_ADDED,
                EditorNotification.ACTOR_UPDATED
        };
    }

    @Override
    public void handleNotification(INotification notification) {
        Scene scene = getViewComponent();

        switch (notification.getName()) {
            case DesignNotification.CREATE -> {
                DesignScreenMediator screenMediator = (DesignScreenMediator) facade.retrieveMediator(DesignScreenMediator.NAME);
                DesignScreen screen = screenMediator.getViewComponent();
                screen.addStage(scene);
                screen.getMultiplexer().addProcessor(scene);

                initInputHandlers();
            }
            case EditorNotification.OPEN_GROUP_EDIT -> {
                ActorWrapper actorWrapper = (ActorWrapper) notification.getBody();
                scene.openGroup((Group) actorWrapper.getActor());
            }
            case EditorNotification.CLOSE_GROUP_EDIT -> scene.closeGroup();
            case EditorNotification.ACTOR_ADDED, EditorNotification.ACTOR_UPDATED -> scene.rebuildPlaceholders();
        }
    }

    private void initInputHandlers() {
        Scene scene = getViewComponent();
        scene.addCaptureListener(new InputListener() {

            private final Vector2 lastTouchPosition = new Vector2();
            private final List<ActorWrapper> selected = new ArrayList<>();
            private boolean selectRoot;
            private long lastTapTime = 0;
            private Actor lastTappedActor = null;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor target = event.getTarget();
                if (button == Input.Buttons.RIGHT) {
                    Actor original = scene.getOriginal(target);
                    if (original instanceof Table || TableUtils.hasCellFor(original)) {
                        facade.sendNotification(EditorNotification.SHOW_CONTEXT_MENU,
                                new ClickInfo(original, event.getStageX(), event.getStageY())                        );
                        return true;
                    }
                    return false;
                }

                selectRoot = target == scene.getRoot();

                // Double click
                long currentTime = System.nanoTime();
                if (currentTime - lastTapTime < TimeUnit.MILLISECONDS.toNanos(300) && target == lastTappedActor) {
                    Actor original = scene.getOriginal(target);
                    facade.sendNotification(EditorNotification.ACTOR_DESELECTED);
                    if (original instanceof Group) {
                        facade.sendNotification(EditorNotification.OPEN_GROUP_EDIT, ActorWrapper.of(target, original));
                    } else {
                        facade.sendNotification(EditorNotification.CLOSE_GROUP_EDIT, getViewComponent().getCurrentGroup());
                    }
                    lastTapTime = 0;
                    lastTappedActor = null;
                    return true;
                }
                lastTapTime = currentTime;
                lastTappedActor = target;

                if (selectRoot) {
                    scene.getSelectionTracker().begin(event.getStageX(), event.getStageY());
                    return true;
                }
                Actor original = scene.getOriginal(target);
                if (original != null) {
                    if (selected.stream().noneMatch(aw -> aw.getActor() == original)) {
                        selected.clear();
                        selected.add(ActorWrapper.of(target, original));
                    }
                    lastTouchPosition.set(x, y);
                    selected.forEach(ActorWrapper::updateOriginalPosition);
                    facade.sendNotification(EditorNotification.ACTOR_SELECTED, selected);
                    return true;
                } else {
                    facade.sendNotification(EditorNotification.ACTOR_DESELECTED);
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                lastTapTime = 0;
                lastTappedActor = null;

                Actor target = event.getTarget();
                if (target == scene.getRoot()) {
                    scene.getSelectionTracker().update(event.getStageX(), event.getStageY());
                    return;
                }
                Actor original = scene.getOriginal(target);
                if (original != null && !selected.isEmpty()) {
                    // Акторы в клетках двигать не можем
                    List<ActorWrapper> wrappers = selected.stream()
                            .filter(aw -> !TableUtils.hasCellFor(aw.getActor()))
                            .toList();
                    if (!wrappers.isEmpty()) {
                        wrappers.forEach(aw -> {
                            Vector2 localCoordinates = scene.getCurrentGroup().stageToLocalCoordinates(new Vector2(x, y));
                            float newX = aw.getOriginalX() + localCoordinates.x - lastTouchPosition.x;
                            float newY = aw.getOriginalY() + localCoordinates.y - lastTouchPosition.y;
                            aw.setTargetPosition(newX, newY);
                        });
                        facade.sendNotification(EditorNotification.ACTOR_MOVED, wrappers);
                    }
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Rectangle rect = scene.getSelectionTracker().getRect();
                selected.clear();
                for (Actor actor : scene.getPlaceholders().getChildren()) {
                    Vector2 stageCoordinates = actor.getParent().localToStageCoordinates(new Vector2(actor.getX(), actor.getY()));
                    if (rect.overlaps(new Rectangle(stageCoordinates.x, stageCoordinates.y, actor.getWidth(), actor.getHeight()))) {
                        selected.add(ActorWrapper.of(actor, scene.getOriginal(actor)));
                    }
                }
                if (!selected.isEmpty())
                    facade.sendNotification(EditorNotification.ACTOR_SELECTED, selected);
                else if(selectRoot) {
                    facade.sendNotification(EditorNotification.ACTOR_DESELECTED);
                }
                scene.getSelectionTracker().end();
                lastTouchPosition.setZero();
            }
        });
    }

}
