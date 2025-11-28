package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.WindowTitle;

public class UpdateWindowTitleCommand extends RevertibleCommand {

    @Override
    public void doAction() {
        WindowTitle windowTitle = (WindowTitle) body;
        ((Window) windowTitle.actor()).getTitleLabel().setText(windowTitle.title());

        facade.sendNotification(EditorNotification.ACTOR_UPDATED, windowTitle.actor());
    }

    @Override
    public void undoAction() {

    }
}
