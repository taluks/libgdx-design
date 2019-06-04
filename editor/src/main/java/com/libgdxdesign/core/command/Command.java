package com.libgdxdesign.core.command;

public interface Command {	
	default String getName() {
		return this.getClass().getSimpleName();
	}
	void execute(CommandExecutor commandExecutor);
	void undo(CommandExecutor commandExecutor);
}
