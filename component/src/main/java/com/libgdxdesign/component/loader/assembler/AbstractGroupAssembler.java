package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.libgdxdesign.component.loader.TableUtils;
import com.libgdxdesign.component.model.ActorData;
import com.libgdxdesign.component.model.GroupData;

public abstract class AbstractGroupAssembler<T extends Group, D extends GroupData> extends ActorAssembler<T, D> {

    @Override
    public T buildParameters(ProxyAssembler proxy, Skin skin, T group, D data) {
        for (ActorData child : data.children) {
            Actor actor = proxy.getBuilder(child.getClass()).build(proxy, skin, child);
            group.addActor(actor);
        }
        return super.buildParameters(proxy, skin, group, data);
    }

    @Override
    public D assembleParameters(ProxyAssembler proxy, Skin skin, D data, T actor) {
        Array<ActorData> children = new Array<>();
        for (Actor child : actor.getChildren()) {
            if (TableUtils.hasCellFor(child)) continue;
            ActorAssembler<Actor, ActorData> assembler = proxy.getAssembler(child.getClass());
            if (assembler == null) {
                Gdx.app.log(this.getClass().getSimpleName(), actor.getClass().getName() + " skipped");
                continue;
            }
            children.add(assembler.assemble(proxy, skin, child));
        }
        data.children = children;
        return super.assembleParameters(proxy, skin, data, actor);
    }
}
