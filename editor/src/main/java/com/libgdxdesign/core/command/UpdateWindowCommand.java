package com.libgdxdesign.core.command;

import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class UpdateWindowCommand implements Command {

	private final Window window;
	private final String title;
	private final String newTitle;
	
	
	public UpdateWindowCommand(Window window, String newTitle) {
		super();
		this.window = window;
		this.title = window.getTitleLabel().getText().toString();
		this.newTitle = newTitle;
	}

	@Override
	public void execute(CommandExecutor commandExecutor) {
		window.getTitleLabel().setText(newTitle);
	}

	@Override
	public void undo(CommandExecutor commandExecutor) {
		window.getTitleLabel().setText(title);
	}

}
