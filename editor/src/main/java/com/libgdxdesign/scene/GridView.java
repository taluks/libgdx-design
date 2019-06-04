package com.libgdxdesign.scene;

import java.util.AbstractList;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GridView extends Group {

	private AbstractList<PixelLine> lines = new ArrayList<PixelLine>();

	public GridView(Skin skin) {

		float gridSize = 10;
		float gridLinesCount = 500;

		for (int i = 0; i < gridLinesCount; i++) {
			PixelLine tmp = new PixelLine(i * gridSize, 0, i * gridSize, gridSize * gridLinesCount);
			opacityHandler(i, tmp);
			addActor(tmp);
			tmp.setX(tmp.getX() - (gridLinesCount / 2 - 1) * gridSize);
			tmp.setY(tmp.getY() - (gridLinesCount / 2 - 1) * gridSize);

			lines.add(tmp);
		}

		for (int i = 0; i < gridLinesCount; i++) {
			PixelLine tmp = new PixelLine(0, i * gridSize, gridSize * gridLinesCount, i * gridSize);
			opacityHandler(i, tmp);
			addActor(tmp);
			tmp.setX(tmp.getX() - (gridLinesCount / 2 - 1) * gridSize);
			tmp.setY(tmp.getY() - (gridLinesCount / 2 - 1) * gridSize);

			lines.add(tmp);
		}

		this.setWidth(gridSize * gridLinesCount);
		this.setHeight(gridSize * gridLinesCount);

		Label lbl = new Label("0.0", skin);
		lbl.setX(-5 - lbl.getWidth());
		lbl.setY(-lbl.getHeight());
		lbl.setColor(Color.BLACK.cpy());
		addActor(lbl);
	}

	private void opacityHandler(float i, PixelLine tmp) {
		i -= 3;
		if (i % 6 == 0) {
			tmp.setOpacity(0.3f);
		} else if (i % 2 == 0) {
			tmp.setOpacity(0.1f);
		} else {
			tmp.setOpacity(0.03f);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		OrthographicCamera camera = (OrthographicCamera) getStage().getCamera();
		for (int i = 0; i < lines.size(); i++) {
			lines.get(i).setThickness(camera.zoom);
		}
	}
}
