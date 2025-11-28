package com.libgdxdesign.scene.view;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SceneController extends InputAdapter {
	
	private final OrthographicCamera camera;
	private final Vector3 current = new Vector3();
	private final Vector3 last = new Vector3();
	private final Vector3 delta = new Vector3();

	public SceneController(Stage stage) {
		this.camera = (OrthographicCamera) stage.getCamera();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		last.set(-1, -1, -1);
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean scrolled(int amount) {
		camera.zoom += amount * 0.1f;
		camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 10f);
		return super.scrolled(amount);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		camera.unproject(current.set(screenX, screenY, 0));
		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			camera.unproject(delta.set(last.x, last.y, 0));
			delta.sub(current);
			camera.position.add(delta.x, delta.y, 0);
		}
		last.set(screenX, screenY, 0);
		return super.touchDragged(screenX, screenY, pointer);
	}
}
