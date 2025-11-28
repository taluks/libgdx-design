package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class CloneableWindow implements CloneableResource<Window> {

    private final Window window;

    public CloneableWindow(Window window) {
        this.window = window;
    }

    @Override
    public Window getActor() {
        return window;
    }

    @Override
    public Window clone() {
        return new Window(window.getTitleLabel().getText().toString(), window.getStyle());
    }
}