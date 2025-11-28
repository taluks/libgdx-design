package com.libgdxdesign.core.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class DesignScreen extends InputAdapter implements Screen {
	
	private final Array<Stage> stages = new Array<>();
	private InputMultiplexer multiplexer;

	public DesignScreen() {

	}

	public void addStage(Stage stage) {
		stages.add(stage);
	}

	@Override
	public void show() {
		multiplexer = new InputMultiplexer(this);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stages.forEach(s -> {
			s.act(delta);
			s.draw();
		});
	}	

	@Override
	public void resize(int width, int height) {
		stages.forEach(s -> s.getViewport().update(width, height, true));
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		stages.forEach(s -> s.dispose());
	}

	public InputMultiplexer getMultiplexer() {
		return multiplexer;
	}

}
