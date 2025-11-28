package com.libgdxdesign.menu.view;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;

public class TopMenu extends Table {

    private final MenuItem saveMenu;
	private final MenuItem about;

	public TopMenu() {

        MenuBar menuBar = new MenuBar();
        Menu projectMenu = new Menu("File");
        menuBar.addMenu(projectMenu);
        Menu helpMenu = new Menu("Help");
        menuBar.addMenu(helpMenu);
        this.add(menuBar.getTable()).left().growX();


        saveMenu = new MenuItem("Save");
        projectMenu.addItem(saveMenu);

	    about = new MenuItem("About");
	    helpMenu.addItem(about);
    }

    public MenuItem getSaveMenu() {
        return saveMenu;
    }

	public MenuItem getAbout() {
		return about;
	}
}
