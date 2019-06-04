package com.libgdxdesign.core.command;


public abstract class AbstractCommand implements Command {

	@Override
	public String getName() {
		return this.getClass().getSimpleName().toLowerCase();
	}


}
