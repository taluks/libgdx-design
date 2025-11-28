package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.CellAlign;
import com.libgdxdesign.component.loader.TableUtils;

public class TableCellAlignCommand extends RevertibleCommand {

    @Override
    public void doAction() {
        CellAlign cellAlign = (CellAlign) body;
        Cell<?> cell = TableUtils.findCell(cellAlign.actor());
        switch (cellAlign.align()) {
            case CENTER -> cell.center();
            case LEFT -> cell.left();
            case RIGHT -> cell.right();
            case TOP -> cell.top();
            case BOTTOM -> cell.bottom();
        }
        cell.getTable().invalidateHierarchy();
        facade.sendNotification(EditorNotification.ACTOR_UPDATED, cellAlign.actor());
    }

    @Override
    public void undoAction() {

    }
}
