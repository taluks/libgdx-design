package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class CloneableTable implements CloneableResource<Table> {

    private final Table table;

    public CloneableTable(Table table) {
        this.table = table;
    }

    @Override
    public Table getActor() {
        return new Table();
    }

    @Override
    public Table clone() {
        Table clone = new Table();
        clone.setSize(table.getWidth(), table.getHeight());
        clone.defaults().pad(table.getPadTop(), table.getPadLeft(), table.getPadBottom(), table.getPadRight());
        Cell<?> defaults = table.defaults();
        clone.defaults().space(defaults.getPadTop(), defaults.getPadLeft(), defaults.getPadBottom(), defaults.getPadRight());
        return clone;
    }
}
