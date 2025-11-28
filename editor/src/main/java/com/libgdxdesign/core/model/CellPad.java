package com.libgdxdesign.core.model;

import com.badlogic.gdx.scenes.scene2d.Actor;

public record CellPad(Actor actor, Type type, float value) {
    public enum Type {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
}