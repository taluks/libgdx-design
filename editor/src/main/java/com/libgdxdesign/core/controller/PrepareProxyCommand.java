package com.libgdxdesign.core.controller;

import com.libgdxdesign.core.proxy.ProjectProxy;
import com.libgdxdesign.core.proxy.SelectionProxy;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import com.libgdxdesign.core.proxy.CommandProxy;

public class PrepareProxyCommand extends SimpleCommand {

	@Override
	public void execute(INotification notification) {
		facade.registerProxy(new CommandProxy());
		facade.registerProxy(new SelectionProxy());
		facade.registerProxy(new ProjectProxy());
	}
}