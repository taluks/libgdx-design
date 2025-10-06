package com.libgdxdesign.core.controller;

import com.libgdxdesign.core.controller.command.AddActorToSceneCommand;
import com.libgdxdesign.core.controller.notification.ResourceNotification;
import com.libgdxdesign.core.controller.notification.SceneNotification;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class BootstrapCommand extends SimpleCommand {

	@Override
	public void execute(INotification notification) {
		facade.registerCommand(ResourceNotification.DROP_FILES, DropFilesCommand::new);

		facade.registerCommand(SceneNotification.ADD_ACTOR, AddActorToSceneCommand::new);
	}
}