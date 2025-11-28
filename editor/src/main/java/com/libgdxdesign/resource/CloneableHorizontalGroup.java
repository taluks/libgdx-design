package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;

public class CloneableHorizontalGroup implements CloneableResource<HorizontalGroup> {

    private final HorizontalGroup horizontalGroup;

    public CloneableHorizontalGroup(HorizontalGroup verticalGroup) {
        this.horizontalGroup = verticalGroup;
    }

    @Override
    public HorizontalGroup getActor() {
        return new HorizontalGroup();
    }

    @Override
    public HorizontalGroup clone() {
        HorizontalGroup clone = new HorizontalGroup();
        clone.setSize(horizontalGroup.getWidth(), horizontalGroup.getHeight());
        return clone;
    }
}
