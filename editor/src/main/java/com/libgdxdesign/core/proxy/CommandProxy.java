package com.libgdxdesign.core.proxy;

import java.util.LinkedList;
import java.util.List;

import org.puremvc.java.patterns.proxy.Proxy;

import com.libgdxdesign.core.controller.command.RevertibleCommand;

public class CommandProxy extends Proxy {

	private final List<RevertibleCommand> commands = new LinkedList<>();
	private int cursor = -1;

	public CommandProxy() {
		super(CommandProxy.class.getCanonicalName());
	}

	public void addCommand(RevertibleCommand revertableCommand) {
		// remove all commands after the cursor
		for (int i = commands.size() - 1; i > cursor; i--) {
			commands.remove(i);
		}
		commands.add(revertableCommand);
		cursor = commands.indexOf(revertableCommand);
	}

	public void undoCommand() {
		if (cursor < 0)
			return;
		RevertibleCommand command = commands.get(cursor);
		if (command.isDone()) {
			command.undoAction();
			command.setDone(false);
		}
		cursor--;
	}

	public void redoCommand() {
		if (cursor + 1 >= commands.size())
			return;
		RevertibleCommand command = commands.get(cursor + 1);
		if (!command.isDone()) {
			cursor++;
			command.doAction();
			command.setDone(true);
		}
	}

	public void clearHistory() {
		cursor = -1;
		commands.clear();
	}
}
