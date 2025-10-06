package com.libgdxdesign.menu.view;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;

public class TopMenu extends Table {

	public TopMenu() {
		setBackground(VisUI.getSkin().getDrawable("button-main-menu"));
		
		MenuBar menuBar = new MenuBar();
		Menu projectMenu = new Menu("File");
		menuBar.addMenu(projectMenu);
		Menu modulesMenu = new Menu("Modules");
		menuBar.addMenu(modulesMenu);
		Menu helpMenu = new Menu("Help");
		MenuItem about = new MenuItem("About");
		helpMenu.addItem(about);
		menuBar.addMenu(helpMenu);
		
		this.add(menuBar.getTable()).left().growX();
	}

	

}
