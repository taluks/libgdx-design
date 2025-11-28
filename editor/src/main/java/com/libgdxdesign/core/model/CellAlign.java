package com.libgdxdesign.core.model;

import com.badlogic.gdx.scenes.scene2d.Actor;

public record CellAlign(Actor actor, Align align) {
    public enum Align {
        CENTER,
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
}