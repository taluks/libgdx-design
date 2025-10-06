package com.libgdxdesign;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFrame;
import com.kotcrab.vis.ui.VisUI;
import com.libgdxdesign.core.controller.StartupCommand;
import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.util.FileDropListener;
import org.puremvc.java.interfaces.IFacade;
import org.puremvc.java.patterns.facade.Facade;

import java.awt.dnd.DropTarget;

public class DesignEditor extends ApplicationAdapter {

	private IFacade facade;

	public DesignEditor() {
	}

	@Override
	public void create() {
		VisUI.load("skin/uiskin.json");
		facade = Facade.getInstance(Facade::new);
		facade.registerCommand(DesignNotification.STARTUP, StartupCommand::new);

		facade.sendNotification(DesignNotification.STARTUP);
		facade.sendNotification(DesignNotification.CREATE);

		new DropTarget(LwjglFrame.getFrames()[0], new FileDropListener(facade));
	}

	@Override
	public void render() {
		facade.sendNotification(DesignNotification.RENDER, Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		facade.sendNotification(DesignNotification.RESIZE, new int[]{width, height});
	}

	@Override
	public void pause() {
		facade.sendNotification(DesignNotification.PAUSE);
	}

	@Override
	public void resume() {
		facade.sendNotification(DesignNotification.RESUME);
	}

	@Override
	public void dispose() {
		facade.sendNotification(DesignNotification.DISPOSE);
		VisUI.dispose();
	}
}
