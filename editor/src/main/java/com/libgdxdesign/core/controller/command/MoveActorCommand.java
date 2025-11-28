package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.math.Vector2;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveActorCommand extends RevertibleCommand {

    private final Map<ActorWrapper, Vector2> oldPositions = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public void doAction() {
        if (!(body instanceof List<?>))
            return;

        List<ActorWrapper> actors = (List<ActorWrapper>) body;

        for (ActorWrapper wrapper : actors) {
            oldPositions.put(wrapper, new Vector2(wrapper.getActor().getX(), wrapper.getActor().getY()));

            Vector2 newPos = new Vector2(wrapper.getNewX(), wrapper.getNewY());
            wrapper.getActor().setPosition(newPos.x, newPos.y);

            // Placeholders отображаются всегда без контейнера с координатами сцены
            Vector2 stageCoordinates = wrapper.getActor().getParent().localToStageCoordinates(newPos.cpy());
            wrapper.getPlaceholder().setPosition(stageCoordinates.x, stageCoordinates.y);
        }
        facade.sendNotification(EditorNotification.ACTOR_TRANSFORM_UPDATED, actors);
    }

    @Override
    public void undoAction() {
        for (Map.Entry<ActorWrapper, Vector2> entry : oldPositions.entrySet()) {
            ActorWrapper wrapper = entry.getKey();
            Vector2 oldPos = entry.getValue();
            wrapper.getActor().setPosition(oldPos.x, oldPos.y);

            Vector2 stageCoordinates = wrapper.getActor().getParent().localToStageCoordinates(oldPos.cpy());
            wrapper.getPlaceholder().setPosition(stageCoordinates.x, stageCoordinates.y);
        }
        facade.sendNotification(EditorNotification.ACTOR_TRANSFORM_UPDATED, oldPositions.keySet());
    }
}
