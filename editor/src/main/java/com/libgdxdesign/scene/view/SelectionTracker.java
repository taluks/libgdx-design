package com.libgdxdesign.scene.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SelectionTracker {

    private static final Color COLOR = Color.GREEN.cpy();
    private final Rectangle rect = new Rectangle();

    public void begin(float x, float y) {
        rect.setPosition(x, y);
    }

    public void update(float x, float y) {
        float width = x - rect.x;
        float height = y - rect.y;
        rect.setSize(width, height);
    }

    public void render(Viewport viewport, ShapeRenderer renderer) {
        if (rect.area() == 0)
            return;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeType.Filled);
        COLOR.a = 0.2f;
        renderer.setColor(COLOR);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
        renderer.set(ShapeType.Line);
        COLOR.a = 0.6f;
        renderer.setColor(COLOR);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void end() {
        rect.set(0f, 0f, 0f, 0f);
    }

    public Rectangle getRect() {
        float x = rect.x;
        float y = rect.y;
        float width = rect.width;
        float height = rect.height;
        if (width < 0) {
            x = x + width;
            width = rect.x - x;
        }
        if (height < 0) {
            y = y + height;
            height = rect.y - y;
        }
        return rect.set(x, y, width, height);
    }
}
