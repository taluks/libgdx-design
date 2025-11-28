package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.component.loader.TableUtils;

public class TableClearChildrenCommand extends RevertibleCommand {

    @Override
    public void doAction() {
        Actor actor = (Actor) body;
        if (actor instanceof Table table) {
            table.clearChildren();
            facade.sendNotification(EditorNotification.ACTOR_UPDATED, table);
        } else {
            Table table = TableUtils.findCell(actor).getTable();
            table.clearChildren();
            facade.sendNotification(EditorNotification.ACTOR_UPDATED, table);
        }
    }

    @Override
    public void undoAction() {

    }
}
