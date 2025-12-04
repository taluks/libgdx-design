package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.libgdxdesign.component.model.WindowData;

public class WindowAssembler extends AbstractWindowAssembler<Window, WindowData> {

    @Override
    public Window build(ProxyAssembler proxy, Skin skin, WindowData data) {
        Window window = new Window(data.title, skin, data.styleName);
        return buildParameters(proxy, skin, window, data);
    }

    @Override
    public WindowData assemble(ProxyAssembler proxy, Skin skin, Window actor) {
        return assembleParameters(proxy, skin, new WindowData(), actor);
    }
}