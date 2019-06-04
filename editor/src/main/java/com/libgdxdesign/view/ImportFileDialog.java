package com.libgdxdesign.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ImportFileDialog extends BaseDialog {

	private Table mainTable;

	public ImportFileDialog(Skin skin) {
		super("Import file", skin);

		mainTable = new Table();
		add(mainTable).fill().expand();

		Label helpLbl = new Label("Supported files Skin or UI", skin);
		helpLbl.setWidth(260);
		helpLbl.setWrap(true);
		mainTable.add(helpLbl).width(260).padLeft(5);
		mainTable.row().padBottom(5);

		Image dropRegion = new Image(skin.getDrawable("drop-here"));
		mainTable.add(dropRegion).padRight(6).padBottom(6).padTop(10);
		mainTable.row().pad(5);

		mainTable.add(new Label("or browse files on file system", skin));
		mainTable.row().pad(5);

		TextButton showFileSelectBtn = new TextButton("Browse", skin);
		mainTable.add(showFileSelectBtn).width(88);
		mainTable.row().pad(5);
		showFileSelectBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				FileChooser files = FileChooser.createLoadDialog("Load files", skin, Gdx.files.absolute("/"));
				files.show(getStage());
				files.setResultListener(new FileChooser.ResultListener() {				
					@Override
					public boolean result(boolean success, FileHandle result) {
						
						return false;
					}
				});
			}
		});
		pack();
	}

}
