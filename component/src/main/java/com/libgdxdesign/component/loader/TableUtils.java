package com.libgdxdesign.component.loader;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TableUtils {

    public static boolean hasCellFor(Actor actor) {
        return findCell(actor) != null;
    }

    public static Cell<?> findCell(Actor actor) {
        if (actor != null && actor.getParent() instanceof Table table) {
            for (Cell<?> cell : table.getCells()) {
                if (cell.getActor() == actor) return cell;
            }
        }
        return null;
    }
}
