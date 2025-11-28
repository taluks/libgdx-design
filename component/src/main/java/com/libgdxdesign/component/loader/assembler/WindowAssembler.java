package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.libgdxdesign.component.loader.TableUtils;
import com.libgdxdesign.component.model.ActorData;
import com.libgdxdesign.component.model.WindowData;

public class WindowAssembler extends AbstractTableAssembler<Window, WindowData> {

    @Override
    public Window build(ProxyAssembler proxy, Skin skin, WindowData data) {
        Window window = new Window(data.title, skin, data.styleName);
        for (ActorData ad : data.children) {
            Actor actor = proxy.getBuilder(ad.getClass()).build(proxy, skin, ad);
            window.addActor(actor);
        }
        window.setModal(data.isModal);
        window.setMovable(data.isMovable);
        window.setResizable(data.isResizable);
        return buildParameters(proxy, skin, window, data);
    }

    @Override
    public WindowData assemble(ProxyAssembler proxy, Skin skin, Window actor) {
        WindowData data = new WindowData();
        data.title = actor.getTitleLabel().getText().toString();
        data.styleName = skin.find(actor.getStyle());
        data.isMovable = actor.isMovable();
        data.isModal = actor.isModal();
        data.isResizable = actor.isResizable();
        Array<ActorData> children = new Array<>();
        for (int i = 0; i < actor.getChildren().size; i++) {
            Actor child = actor.getChildren().get(i);
            if (TableUtils.hasCellFor(child)) continue;
            ActorAssembler<Actor, ActorData> assembler = proxy.getAssembler(child.getClass());
            if (assembler == null) {
                Gdx.app.log(this.getClass().getSimpleName(), actor.getClass().getName() + " skipped");
                continue;
            }
            children.add(assembler.assemble(proxy, skin, child));
        }
        data.children = children;
        return assembleParameters(proxy, skin, data, actor);
    }
}