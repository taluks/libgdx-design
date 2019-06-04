package com.libgdxdesign.core.command;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.libgdxdesign.manager.ImportManager;
import com.libgdxdesign.manager.ProjectManager;
import com.libgdxdesign.utils.Assets;
import com.libgdxdesign.view.EditorScene;
import com.libgdxdesign.view.EditorUI;

@Singleton
public class CommandExecutor {

	@Inject
	public Assets assets;
	public EventBus eventBus;
	@Inject
	public EditorUI ui;
	@Inject
	public EditorScene scene;
	@Inject
	public ImportManager importResourceManager;
	@Inject
	public ProjectManager projectManager;

	private List<Command> history = new ArrayList<>();
	private int virtualSize = 0;

	@Inject
	public CommandExecutor(EventBus eventBus) {
		this.eventBus = eventBus;
		eventBus.register(this);
	}
	
	@Subscribe
	public void updateWindow(UpdateWindowCommand cmd) {
		execute(cmd);
	}
	
	@Subscribe
	public void updateLabel(UpdateLabelCommand cmd) {
		execute(cmd);
	}
	
	@Subscribe
	public void exportUiComponent(ExportUiComponentCommand cmd) {
		execute(cmd);
	}
	
	@Subscribe
	public void updateActorName(ChangeActorNameCommand cmd) {
		execute(cmd);
	}
	
	@Subscribe
	public void deleteActor(DeleteActorCommand cmd) {
		execute(cmd);
	}

	@Subscribe
	public void addActorToScene(AddActorToSceneCommand cmd) {
		execute(cmd);
	}
	
	@Subscribe
	public void transformActor(TransformActorCommand cmd) {
		execute(cmd);
	}

	public void execute(Command command) {
		if (virtualSize != history.size() && virtualSize > 0) {
			history = history.subList(virtualSize - 1, history.size());
		}
		history.add(command);
		virtualSize = history.size();
		command.execute(this);
	}

	public boolean undo() {
		Command command = getUndo();
		if (command == null) {
			return false;
		}
		command.undo(this);
		return true;
	}

	public boolean redo() {
		Command command = getRedo();
		if (command == null) {
			return false;
		}
		command.execute(this);
		return true;
	}

	private Command getUndo() {
		if (virtualSize == 0) {
			return null;
		}
		virtualSize = Math.max(0, virtualSize - 1);
		return history.get(virtualSize);
	}

	private Command getRedo() {
		if (virtualSize == history.size()) {
			return null;
		}
		virtualSize = Math.min(history.size(), virtualSize + 1);
		return history.get(virtualSize - 1);
	}

}
