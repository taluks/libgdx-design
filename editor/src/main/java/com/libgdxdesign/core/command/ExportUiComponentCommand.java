package com.libgdxdesign.core.command;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.libgdxdesign.component.UIComponent;
import com.libgdxdesign.model.Project;

public class ExportUiComponentCommand implements Command {

	private FileHandle fileHandle;

	public ExportUiComponentCommand(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
	}

	@Override
	public void execute(CommandExecutor commandExecutor) {

		Project project = commandExecutor.projectManager.getProject();
		project.setUiComponent(new UIComponent());
		commandExecutor.scene.assembleToProject(project);

		UIComponent uiComponent = project.getUiComponent();
				
		Json json = new Json();
		json.setTypeName(null);
		json.setOutputType(OutputType.javascript);
		if (uiComponent.getActorsDataSet().size == 1) {
			json.toJson(uiComponent.getActorsDataSet().first(), fileHandle);
		} else {
			json.toJson(uiComponent.getActorsDataSet(), fileHandle);
		}
	}

	@Override
	public void undo(CommandExecutor commandExecutor) {

	}

}
