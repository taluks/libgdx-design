package com.libgdxdesign.view.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.common.eventbus.EventBus;
import com.libgdxdesign.core.command.ExportUiComponentCommand;
import com.libgdxdesign.view.FileChooser;
import com.libgdxdesign.view.ImportFileDialog;

public class ToolPanel extends Table {

	private ButtonGroup<Button> buttons = new ButtonGroup<>();
	private Dialog importFileDialog;

	public ToolPanel(EventBus eventBus, Skin skin) {
		setBackground(skin.getDrawable("no-border"));

		defaults().padRight(4).padLeft(4);

		importFileDialog = new ImportFileDialog(skin);

		Button newFileBtn = new ImageButton(skin, "new-file");
		add(newFileBtn).width(newFileBtn.getWidth()).left();

		Button saveFileBtn = new ImageButton(skin, "save-file");
		saveFileBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				FileChooser files = FileChooser.createSaveDialog("Save ui", skin, Gdx.files.external("/"));
				files.show(getStage());
				files.setResultListener(new FileChooser.ResultListener() {
					@Override
					public boolean result(boolean success, FileHandle result) {
						if (result.extension() == "") {
							result = Gdx.files.external(result.file().getAbsolutePath() + ".json");
						} else {
							result = Gdx.files.external(result.file().getAbsolutePath());
						}
						eventBus.post(new ExportUiComponentCommand(result));
						return true;
					}
				});

			}

		});
		add(saveFileBtn).width(saveFileBtn.getWidth()).left();

		Button selectFileBtn = new ImageButton(skin, "select-file");
		selectFileBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				importFileDialog.show(getStage());
				/*
				 * FileChooser files = FileChooser.createLoadDialog("Load ui", skin,
				 * Gdx.files.internal("/")); files.show(getStage());
				 */
			}

		});
		add(selectFileBtn).width(selectFileBtn.getWidth()).expandX().left();

		pack();
	}

	public Dialog getImportFileDialog() {
		return importFileDialog;
	}

	public void setImportFileDialog(Dialog importFileDialog) {
		this.importFileDialog = importFileDialog;
	}

}
