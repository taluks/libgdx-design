package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class CloneableVerticalGroup implements CloneableResource<VerticalGroup> {

    private final VerticalGroup verticalGroup;

    public CloneableVerticalGroup(VerticalGroup verticalGroup) {
        this.verticalGroup = verticalGroup;
    }

    @Override
    public VerticalGroup getActor() {
        return new VerticalGroup();
    }

    @Override
    public VerticalGroup clone() {
        VerticalGroup clone = new VerticalGroup();
        clone.setSize(verticalGroup.getWidth(), verticalGroup.getHeight());
        return clone;
    }
}
