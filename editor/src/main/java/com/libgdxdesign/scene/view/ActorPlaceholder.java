package com.libgdxdesign.scene.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.libgdxdesign.utils.TextureUtils;

public class ActorPlaceholder extends Actor {

    private final Image image;

    public ActorPlaceholder(Actor actor, float x, float y) {
        setBounds(x, y, actor.getWidth(), actor.getHeight());
        setTouchable(Touchable.enabled);
        setName(actor.getName());

        image = new Image(TextureUtils.createTexture());
    }

    public static ActorPlaceholder from(Actor actor, float x, float y) {
        return new ActorPlaceholder(actor, x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        image.setPosition(getX(), getY());
        image.setSize(getWidth(), getHeight());
        image.draw(batch, 0.3f);
    }
}
