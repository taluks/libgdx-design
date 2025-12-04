package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.model.GroupData;

public class GroupAssembler extends AbstractGroupAssembler<Group, GroupData> {

    @Override
    public Group build(ProxyAssembler proxy, Skin skin, GroupData data) {
        return buildParameters(proxy, skin, new Group(), data);
    }

    @Override
    public GroupData assemble(ProxyAssembler proxy, Skin skin, Group actor) {
        return assembleParameters(proxy, skin, new GroupData(), actor);
    }
}
