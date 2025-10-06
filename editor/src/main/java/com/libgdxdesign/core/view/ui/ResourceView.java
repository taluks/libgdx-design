package com.libgdxdesign.core.view.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;

public class ResourceView extends TabbedPane {

	public ResourceView() {

		Tab tab = new ResourceTab("test");
		add(tab);
		
		tab = new ResourceTab("new tab 2");

		add(tab);
		
		tab = new ResourceTab("gdfsgsfdgsdf");

		add(tab);
	}

	class ResourceTab extends Tab {

		private String title;

		public ResourceTab(String title) {
			super(true, false);
			this.title = title;
		}

		@Override
		public String getTabTitle() {
			return title;
		}

		@Override
		public Table getContentTable() {
			return null;
		}

	}

}
