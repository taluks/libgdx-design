package com.libgdxdesign.core.command;

import java.awt.dnd.DropTargetDropEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.libgdxdesign.component.UIComponent;
import com.libgdxdesign.model.Project;

public class DropFilesCommand extends AbstractCommand {

	private DropTargetDropEvent dtde;

	public DropFilesCommand(DropTargetDropEvent dtde) {
		this.dtde = dtde;
	}

	@Override
	public void execute(CommandExecutor commandExecutor) {
		String[] paths = commandExecutor.importResourceManager.catchFiles(dtde);
		Array<FileHandle> files = commandExecutor.importResourceManager.getFilesFromPaths(paths);

		Project project = commandExecutor.projectManager.getProject();
		String path = files.first().path();
		if (loadSkin(commandExecutor, path)) {
			Skin skin = commandExecutor.assets.get(path);
			project.setSkin(skin);
			commandExecutor.ui.displayResources();
		} else if (loadDesign(commandExecutor, path)) {
			UIComponent uiComponent = commandExecutor.assets.get(path);
			project.setUiComponent(uiComponent);
			// TODO show error wnd
			if (project.getSkin() == null) {
				Gdx.app.log(getClass().getSimpleName(), "Load skin first");
				return;
			}
			commandExecutor.scene.setProject(project);
		} else {
			Gdx.app.log(getClass().getSimpleName(), "File not supported");
		}
		commandExecutor.ui.getToolPanel().getImportFileDialog().hide();
	}

	private boolean loadSkin(CommandExecutor commandExecutor, String path) {
		try {
			commandExecutor.assets.load(path, Skin.class);
			commandExecutor.assets.finishLoading();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean loadDesign(CommandExecutor commandExecutor, String path) {
		try {
			if (commandExecutor.assets.isLoaded(path)) {
				commandExecutor.assets.unload(path);
			}
			commandExecutor.assets.load(path, UIComponent.class);
			commandExecutor.assets.finishLoading();
		} catch (Exception e) {
			e.fillInStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void undo(CommandExecutor commandExecutor) {
		// TODO Auto-generated method stub

	}

}
