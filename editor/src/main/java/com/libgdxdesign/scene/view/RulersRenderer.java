package com.libgdxdesign.scene.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RulersRenderer extends Actor {

	private final Stage stage;
	private final OrthographicCamera camera;

	private Vector2 tmp = new Vector2();
	private float tileSize = 10f;

	private ShapeRenderer shapeRenderer = new ShapeRenderer();

	public RulersRenderer(Stage stage) {
		this.stage = stage;
		this.camera = (OrthographicCamera) stage.getCamera();

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();

		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

		final float worldWidth = stage.getViewport().getWorldWidth() * camera.zoom;
		final float worldHeight = stage.getViewport().getWorldHeight() * camera.zoom;

		final Vector3 position = stage.getViewport().getCamera().position;

		float x = position.x - worldWidth / 2f;
		float y = position.y - worldHeight / 2f;
		tmp.set(camera.position.x, camera.position.y);

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		drawVerticalRuler(x, y, worldWidth, worldHeight);
		drawHorisontalRuler(x, y, worldWidth, worldHeight);
		shapeRenderer.end();

		batch.begin();
		
		
	}

	private void drawVerticalRuler(float x, float y, float worldWidth, float worldHeight) {
		float rulerSize = 14f * camera.zoom;
		shapeRenderer.setColor(1f, 1f, 1f, 0.8f);
		shapeRenderer.box(x, y, 0, rulerSize, worldHeight, 0);

		float thickness = 2f * camera.zoom;

		int lineCount = (int) (worldWidth / tileSize);
		int blackLineCount = (int) (worldWidth / (tileSize * 10));
		float width = worldWidth;

		for (int i = -lineCount / 2 - 1; i < lineCount / 2 + 1; i++) {
			float spacing = width / lineCount;
			shapeRenderer.setColor(0.12f, 0.12f, 0.12f, 1f);
			float posX = tmp.x - worldWidth / 2f + rulerSize;
			float posY = tmp.y + i * spacing - tmp.y % spacing;
			shapeRenderer.rectLine(posX - 4f * camera.zoom, posY, posX, posY, thickness);
		}

		for (int i = -blackLineCount / 2 - 1; i < blackLineCount / 2 + 1; i++) {
			float spacing = width / blackLineCount;
			shapeRenderer.setColor(0.12f, 0.12f, 0.12f, 1f);
			float posX = tmp.x - worldWidth / 2f + rulerSize;
			float posY = tmp.y + i * spacing - tmp.y % spacing;
			shapeRenderer.rectLine(posX, posY, posX - rulerSize, posY, thickness);
		}
	}

	private void drawHorisontalRuler(float x, float y, float worldWidth, float worldHeight) {
		float rulerHeight = 14f * camera.zoom;
		shapeRenderer.setColor(1f, 1f, 1f, 0.8f);
		shapeRenderer.box(x, y, 0, worldWidth, rulerHeight, 0);

		float thickness = 2f * camera.zoom;

		int lineCount = (int) (worldWidth / tileSize);
		int blackLineCount = (int) (worldWidth / (tileSize * 10));
		float width = worldWidth;

		for (int i = -lineCount / 2 - 1; i < lineCount / 2 + 1; i++) {
			float spacing = width / lineCount;
			shapeRenderer.setColor(0.12f, 0.12f, 0.12f, 1f);
			float posX = tmp.x - i * spacing - tmp.x % spacing;
			float posY = tmp.y - worldHeight / 2f + rulerHeight;
			shapeRenderer.rectLine(posX, posY, posX, posY - 4f * camera.zoom, thickness);
		}

		for (int i = -blackLineCount / 2 - 1; i < blackLineCount / 2 + 1; i++) {
			float spacing = width / blackLineCount;
			shapeRenderer.setColor(0.12f, 0.12f, 0.12f, 1f);
			float posX = tmp.x - i * spacing - tmp.x % spacing;
			float posY = tmp.y - worldHeight / 2f + rulerHeight;
			shapeRenderer.rectLine(posX, posY, posX, posY - rulerHeight, thickness);
		}

	}

}
