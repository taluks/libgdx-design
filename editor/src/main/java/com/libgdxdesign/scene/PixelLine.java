package com.libgdxdesign.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.libgdxdesign.utils.TextureUtils;

public class PixelLine extends Image {

	private float thickness = 1;

	public PixelLine(float x, float y, float endX, float endY) {
		super(TextureUtils.createTexture());
		setPosition(x, y, endX, endY);
		setScaleY(thickness);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public void setPosition(float x, float y, float endX, float endY) {
		this.setX(x);
		this.setY(y);

		double width = Math.sqrt((endX - x) * (endX - x) + (endY - y) * (endY - y));
		this.setScaleX((float) width);
		this.setRotation(90 - getAngle(x, y, endX, endY));
	}

	public void setOpacity(float opacity) {
		Color clr = getColor();
		clr.a = opacity;
		setColor(clr);
	}

	private float getAngle(float x, float y, float endX, float endY) {
		float angle = (float) Math.toDegrees(Math.atan2(endX - x, endY - y));
		return angle < 0 ? angle + 360 : angle;
	}

	public float getThickness() {
		return thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
		this.setScaleY(thickness);
	}

}
