package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.model.ImageButtonData;

public class ImageButtonAssembler extends ActorAssembler<ImageButton, ImageButtonData> {

    @Override
    public ImageButton build(ProxyAssembler proxy, Skin skin, ImageButtonData data) {
        ImageButton button = new ImageButton(skin, data.styleName);
        return buildParameters(proxy, skin, button, data);
    }

    @Override
    public ImageButtonData assemble(ProxyAssembler proxy, Skin skin, ImageButton actor) {
        ImageButtonData data = new ImageButtonData();
        data.styleName = skin.find(actor.getStyle());
        return assembleParameters(proxy, skin, data, actor);
    }
}