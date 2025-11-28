package com.libgdxdesign.core.model;

import com.badlogic.gdx.scenes.scene2d.Actor;

public record ActorPosition(Actor actor, Axis axis, Object value) {
    public enum Axis {
        X,
        Y,
        Z,
    }
}
