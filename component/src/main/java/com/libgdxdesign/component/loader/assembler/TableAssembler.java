package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.libgdxdesign.component.model.TableData;

public class TableAssembler extends AbstractTableAssembler<Table, TableData> {

    @Override
    public Table build(ProxyAssembler proxy, Skin skin, TableData data) {
        Table table = new Table(skin);
        return buildParameters(proxy, skin, table, data);
    }


    @Override
    public TableData assemble(ProxyAssembler proxy, Skin skin, Table actor) {
        TableData data = new TableData();
        return assembleParameters(proxy, skin, data, actor);
    }
}
