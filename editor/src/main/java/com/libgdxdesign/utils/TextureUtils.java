package com.libgdxdesign.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class TextureUtils {

	public static Texture createTexture() {
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		return new Texture(pixmap);
	}

	public static NinePatchDrawable border(Color color, int thickness) {
		int size = thickness * 3;
		int w = size, h = size;
		Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);

		p.setColor(0,0,0,0);
		p.fill();

		p.setColor(color);

		p.fillRectangle(0, h - thickness, w, thickness);
		p.fillRectangle(0, 0, w, thickness);

		p.fillRectangle(0, 0, thickness, h);
		p.fillRectangle(w - thickness, 0, thickness, h);

		Texture tex = new Texture(p);
		p.dispose();

		return new NinePatchDrawable(new NinePatch(tex, thickness, thickness, thickness, thickness));
	}
}
