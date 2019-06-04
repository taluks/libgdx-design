package com.libgdxdesign.view;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class LayerPanel extends Window {

	private ImageButton newBtn;
	private Table bottomPane;
	private Table contentTable;

	public LayerPanel(Skin skin) {
		super("Layers", skin, "box");

		contentTable = new Table();
		bottomPane = new Table();
		contentTable.row();
		contentTable.add(bottomPane).expandX().fillX();
		add(contentTable).expand().fill();
		
		newBtn = new ImageButton(skin, "new-layer-button");
		bottomPane.add(newBtn);
	}

}
