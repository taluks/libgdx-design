package com.libgdxdesign.ui.view;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

public class TabView extends Tab {

    private String title;
    private Table content;

    public TabView(String title, Table content) {
        super(false, false);
        this.title = title;
        this.content = content;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public Table getContentTable() {
        return content;
    }
}
