package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.component.loader.TableUtils;

public class TableCellResetCommand extends RevertibleCommand {

    @Override
    public void doAction() {
        Actor actor = (Actor) body;
        Cell<?> cell = TableUtils.findCell(actor);
        cell.fill(false).expand(false, false);
        cell.getTable().invalidateHierarchy();
        facade.sendNotification(EditorNotification.ACTOR_UPDATED, actor);
    }

    @Override
    public void undoAction() {

    }
}
