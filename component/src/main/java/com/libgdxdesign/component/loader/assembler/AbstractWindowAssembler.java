package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.libgdxdesign.component.model.WindowData;

public abstract class AbstractWindowAssembler<T extends Window, D extends WindowData> extends AbstractTableAssembler<T, D> {

    @Override
    public T buildParameters(ProxyAssembler proxy, Skin skin, T window, D data) {
        window.setModal(data.isModal);
        window.setMovable(data.isMovable);
        window.setResizable(data.isResizable);
        return super.buildParameters(proxy, skin, window, data);
    }

    @Override
    public D assembleParameters(ProxyAssembler proxy, Skin skin, D data, T actor) {
        data.title = actor.getTitleLabel().getText().toString();
        data.styleName = skin.find(actor.getStyle());
        data.isMovable = actor.isMovable();
        data.isModal = actor.isModal();
        data.isResizable = actor.isResizable();
        return super.assembleParameters(proxy, skin, data, actor);
    }
}
