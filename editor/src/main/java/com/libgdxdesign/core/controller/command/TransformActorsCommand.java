package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorWrapper;
import com.libgdxdesign.core.model.TransformData;

import java.util.ArrayList;
import java.util.List;

public class TransformActorsCommand extends RevertibleCommand {

    private final List<ActorState> originalStates = new ArrayList<>();

    @Override
    public void doAction() {
        TransformData transformData = (TransformData) body;
        List<ActorWrapper> actors = transformData.actors();

        for (ActorWrapper actorWrapper : actors) {
            Actor actor = actorWrapper.getActor();
            originalStates.add(new ActorState(
                    actor.getX(), actor.getY(),
                    actor.getWidth(), actor.getHeight(),
                    actor.getScaleX(), actor.getScaleY()
            ));
        }

        applyProportionalTransformation(transformData.newPosition(), transformData.newSize());
    }

    @Override
    public void undoAction() {
        TransformData transformData = (TransformData) body;
        List<ActorWrapper> actors = transformData.actors();

        for (int i = 0; i < actors.size(); i++) {
            ActorWrapper actorWrapper = actors.get(i);
            ActorState state = originalStates.get(i);
            Actor actor = actorWrapper.getActor();
            Actor placeholder = actorWrapper.getPlaceholder();

            actor.setPosition(state.x, state.y);
            actor.setSize(state.width, state.height);
            actor.setScale(state.scaleX, state.scaleY);
            placeholder.setPosition(state.x, state.y);
            placeholder.setSize(state.width, state.height);
            placeholder.setScale(state.scaleX, state.scaleY);
        }

        facade.sendNotification(EditorNotification.ACTOR_TRANSFORM_UPDATED, actors);
    }

    private void applyProportionalTransformation(Vector2 targetPosition, Vector2 targetSize) {
        TransformData transformData = (TransformData) body;
        List<ActorWrapper> actors = transformData.actors();
        Vector2 originalSize = transformData.originalSize();
        Vector2 originalPosition = transformData.originalPosition();
        float scaleX = targetSize.x / originalSize.x;
        float scaleY = targetSize.y / originalSize.y;

        for (ActorWrapper actorWrapper : actors) {
            Actor actor = actorWrapper.getActor();
            Actor placeholder = actorWrapper.getPlaceholder();

            // Получаем мировые координаты актора относительно оригинальной группы
            Vector2 worldPos = new Vector2(actor.getX(), actor.getY());
            actor.getParent().localToStageCoordinates(worldPos);

            // Вычисляем относительное положение внутри группы
            float relativeX = (worldPos.x - originalPosition.x) / originalSize.x;
            float relativeY = (worldPos.y - originalPosition.y) / originalSize.y;

            // Вычисляем новые мировые координаты
            Vector2 newWorldPos = new Vector2(
                    targetPosition.x + relativeX * targetSize.x,
                    targetPosition.y + relativeY * targetSize.y
            );

            Vector2 localPos = actor.getParent().stageToLocalCoordinates(newWorldPos.cpy());
            actor.setPosition(localPos.x, localPos.y);
            actor.setSize(actor.getWidth() * scaleX, actor.getHeight() * scaleY);

            if (placeholder != null) {
                placeholder.setPosition(newWorldPos.x, newWorldPos.y);
                placeholder.setSize(actor.getWidth(), actor.getHeight());
            }
        }
    }

    private static class ActorState {
        float x, y;
        float width, height;
        float scaleX, scaleY;

        ActorState(float x, float y, float width, float height, float scaleX, float scaleY) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }
    }
}