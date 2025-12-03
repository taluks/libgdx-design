package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.libgdxdesign.component.model.CellData;
import com.libgdxdesign.component.model.TableData;

public abstract class AbstractTableAssembler<T extends Table, D extends TableData> extends ActorAssembler<T, D> {

    @Override
    public T buildParameters(ProxyAssembler proxy, Skin skin, T table, D data) {
        for (CellData cellData : data.cells) {
            Actor actor = proxy.getBuilder(cellData.actor.getClass()).build(proxy, skin, cellData.actor);
            Cell<Actor> cell = table.add(actor);
            cell.align(cellData.align);
            cell.pad(cellData.padTop, cellData.padLeft, cellData.padBottom, cellData.padRight);
            cell.colspan(cellData.colspan);
            cell.expand(cellData.expandX, cellData.expandY);
            if (cellData.fillX > 0 || cellData.fillY > 0) cell.fill(cellData.fillX, cellData.expandY);
            if (cellData.endRow) table.row();
        }
        table.invalidateHierarchy();
        return super.buildParameters(proxy, skin, table, data);
    }

    @Override
    public D assembleParameters(ProxyAssembler proxy, Skin skin, D data, T table) {
        data.cells = new Array<>();
        for (Cell<?> cell : table.getCells()) {
            CellData cellData = new CellData();
            cellData.padLeft = cell.getPadLeft();
            cellData.padTop = cell.getPadTop();
            cellData.padRight = cell.getPadRight();
            cellData.padBottom = cell.getPadBottom();
            cellData.align = cell.getAlign();
            cellData.colspan = cell.getColspan();
            cellData.endRow = cell.isEndRow();
            cellData.fillY = cell.getFillY();
            cellData.fillX = cell.getFillX();
            cellData.expandX = cell.getExpandX();
            cellData.expandY = cell.getExpandY();
            if (cell.hasActor())
                cellData.actor = proxy.getAssembler(cell.getActor().getClass())
                        .assemble(proxy, skin, cell.getActor());
            data.cells.add(cellData);
        }
        return super.assembleParameters(proxy, skin, data, table);
    }
}