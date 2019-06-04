package com.libgdxdesign;

import java.awt.dnd.DropTarget;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.backends.lwjgl.LwjglFrame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.libgdxdesign.core.HotKeyListener;
import com.libgdxdesign.core.command.CommandExecutor;
import com.libgdxdesign.core.event.ResizeEvent;
import com.libgdxdesign.core.resource.parser.SkinParser;
import com.libgdxdesign.manager.ProjectManager;
import com.libgdxdesign.utils.Assets;
import com.libgdxdesign.view.EditorScene;
import com.libgdxdesign.view.EditorUI;
import com.libgdxdesign.view.frame.FileDropListener;

public class DesignEditor extends ApplicationAdapter {

	private static final Color bgColor =  Color.valueOf("#2a2a2a");
	
	private EditorScene scene;
	private EditorUI ui;
	private EventBus eventBus;

	public DesignEditor() {

	}

	@Override
	public void create() {

		Injector injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(EventBus.class).asEagerSingleton();
				bind(Assets.class);
				bind(EditorUI.class);
				bind(CommandExecutor.class);
				bind(EditorScene.class);
				bind(ProjectManager.class).asEagerSingleton();
				bind(FileDropListener.class);				
				bind(SkinParser.class).asEagerSingleton();
			}

		});

		Assets assets = injector.getInstance(Assets.class);
		assets.load();
		
		new DropTarget(LwjglFrame.getFrames()[0], injector.getInstance(FileDropListener.class));
		
		eventBus = injector.getInstance(EventBus.class);
		scene = injector.getInstance(EditorScene.class);
		ui = injector.getInstance(EditorUI.class);
		injector.getInstance(CommandExecutor.class);
		
		InputMultiplexer multiplexer = new InputMultiplexer();		
		multiplexer.addProcessor(ui);
		multiplexer.addProcessor(scene);
		multiplexer.addProcessor(new HotKeyListener(eventBus));
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		scene.getViewport().update(width, height, true);
		ui.getViewport().update(width, height, true);
		eventBus.post(new ResizeEvent(width, height));
	}

	@Override
	public void render() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		scene.act(Gdx.graphics.getDeltaTime());
		scene.draw();

		ui.act(Gdx.graphics.getDeltaTime());
		ui.draw();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
		scene.dispose();
		ui.dispose();
	}
}
