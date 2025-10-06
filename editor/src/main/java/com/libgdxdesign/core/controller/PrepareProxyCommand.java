package com.libgdxdesign.core.controller;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import com.libgdxdesign.core.proxy.CommandProxy;

public class PrepareProxyCommand extends SimpleCommand {

	@Override
	public void execute(INotification notification) {
		facade.registerProxy(new CommandProxy());
	}
}