package com.libgdxdesign.scene.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.libgdxdesign.utils.TextureUtils;

public class TableCell extends Container<Actor> {

    private static final NinePatchDrawable RED_BORDER_DRAWABLE = TextureUtils.border(Color.RED, 2);

    public TableCell() {
        setBackground(RED_BORDER_DRAWABLE);
        pad(4);
        setActor(new VisLabel("cell", Color.RED));
    }
}
