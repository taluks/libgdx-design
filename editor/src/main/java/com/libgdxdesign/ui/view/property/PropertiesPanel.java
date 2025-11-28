package com.libgdxdesign.ui.view.property;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.VisUI;

import java.util.ArrayList;
import java.util.List;

public class PropertiesPanel extends Table {


    private List<AbstractPropertiesPanel<?>> propertiesPanels = new ArrayList<>();
    private Table propsTable = new Table();

    public PropertiesPanel() {
        Skin skin = VisUI.getSkin();

        BaseProperties baseProperties = new BaseProperties(skin);
        propsTable.add(baseProperties).top().left().row();

        addProperties(baseProperties);
        addProperties(new LabelProperties(skin));
        addProperties(new WindowProperties(skin));
        addProperties(new CellProperties(skin));

        add(propsTable).expand().top().left().pad(4);
        pack();
    }

    public void clearProperties() {
        propsTable.clear();
    }

    public void showProperties(Actor actor) {
        propsTable.clear();
        for (AbstractPropertiesPanel<?> pp : propertiesPanels) {
            if (actor == null) continue;
            if (pp.canShowFor(actor)) {
                propsTable.add(pp).top().left().row();
                pp.update(actor);
            }
        }
    }

    private AbstractPropertiesPanel<?> addProperties(AbstractPropertiesPanel<?> panel) {
        propertiesPanels.add(panel);
        return panel;
    }

}