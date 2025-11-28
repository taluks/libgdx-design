package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorPosition;

public class UpdateActorPositionCommand extends RevertibleCommand {

    @SuppressWarnings("unchecked")
    @Override
    public void doAction() {
        ActorPosition actorPosition = (ActorPosition) body;
        Actor actor = actorPosition.actor();
        switch (actorPosition.axis()) {
            case X -> actor.setX((float)actorPosition.value());
            case Y -> actor.setY((float)actorPosition.value());
            case Z -> actor.setZIndex((int)actorPosition.value());
        }
        facade.sendNotification(EditorNotification.ACTOR_UPDATED, actor);
    }

    @Override
    public void undoAction() {
    }
}
