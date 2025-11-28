package com.libgdxdesign.core.controller.command;

import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorSize;

public class UpdateActorHeightCommand extends RevertibleCommand {

    @SuppressWarnings("unchecked")
    @Override
    public void doAction() {
        ActorSize actorSize = (ActorSize) body;
        actorSize.actor().setHeight(actorSize.value());
        facade.sendNotification(EditorNotification.ACTOR_UPDATED, actorSize.actor());
    }

    @Override
    public void undoAction() {
    }
}
