package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Container;

public class CloneableContainer implements CloneableResource<Container<?>> {

    private final Container<?> container;

    public CloneableContainer(Container<?> container) {
        this.container = container;
    }

    @Override
    public Container<?> getActor() {
        return new Container<>();
    }

    @Override
    public Container<?> clone() {
        Container<?> clone = new Container<>();
        clone.setSize(container.getWidth(), container.getHeight());
        return clone;
    }
}
