package com.libgdxdesign.core.controller.command;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import com.libgdxdesign.core.proxy.CommandProxy;

public abstract class RevertibleCommand extends SimpleCommand {

	protected CommandProxy commandProxy;

	protected Object body;
	protected boolean cancelled = false;
	protected boolean done = false;

	@Override
	public void execute(INotification notification) {
		body = notification.getBody();
		doAction();
		setDone(true);
		commandProxy = (CommandProxy) facade.retrieveProxy(CommandProxy.class.getCanonicalName());
		commandProxy.addCommand(this);
	}
	public abstract void doAction();
	public abstract void undoAction();

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
}
