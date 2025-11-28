package com.libgdxdesign.core.controller;

import org.puremvc.java.patterns.command.MacroCommand;

public class StartupCommand extends MacroCommand {

	@Override
	protected void initializeMacroCommand() {
		addSubCommand(PrepareProxyCommand::new);
		addSubCommand(BootstrapViewCommand::new);
		addSubCommand(BootstrapCommand::new);
		addSubCommand(EditorCommand::new);
	}

}
