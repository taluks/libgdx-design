package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorSize;
import com.libgdxdesign.core.model.LabelText;

public class UpdateLabelTextCommand extends RevertibleCommand {

    @SuppressWarnings("unchecked")
    @Override
    public void doAction() {
        LabelText labelText = (LabelText) body;
        ((Label) labelText.actor()).setText(labelText.text());
        facade.sendNotification(EditorNotification.ACTOR_UPDATED, labelText.actor());
    }

    @Override
    public void undoAction() {
    }
}
