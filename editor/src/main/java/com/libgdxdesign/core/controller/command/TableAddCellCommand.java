package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.scene.view.TableCell;
import com.libgdxdesign.component.loader.TableUtils;

public class TableAddCellCommand extends RevertibleCommand {

    @Override
    public void doAction() {
        Actor actor = (Actor) body;
        if (actor instanceof Table table) table.add(new TableCell());
        else TableUtils.findCell(actor).getTable().add(new TableCell());

        facade.sendNotification(EditorNotification.ACTOR_UPDATED, actor);
    }

    @Override
    public void undoAction() {

    }
}
