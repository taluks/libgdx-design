package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.CellPad;
import com.libgdxdesign.component.loader.TableUtils;

public class TableCellPadCommand extends RevertibleCommand {

    @Override
    public void doAction() {
        CellPad cellPad = (CellPad) body;
        Cell<?> cell = TableUtils.findCell(cellPad.actor());
        switch (cellPad.type()) {
            case LEFT -> cell.padLeft(cellPad.value());
            case RIGHT -> cell.padRight(cellPad.value());
            case TOP ->  cell.padTop(cellPad.value());
            case BOTTOM -> cell.padBottom(cellPad.value());
        }
        cell.getTable().invalidateHierarchy();
        facade.sendNotification(EditorNotification.ACTOR_UPDATED, cellPad.actor());
    }

    @Override
    public void undoAction() {

    }
}
