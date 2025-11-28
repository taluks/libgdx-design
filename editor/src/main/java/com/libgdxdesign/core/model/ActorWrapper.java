package com.libgdxdesign.core.model;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorWrapper {

    private final Actor placeholder;
    private final Actor actor;

    private float originalX, originalY;
    private float newX, newY;

    public ActorWrapper(Actor placeholder, Actor actor) {
        this.placeholder = placeholder;
        this.actor = actor;
    }

    public static ActorWrapper of(Actor placeholder, Actor actor) {
        return new ActorWrapper(placeholder, actor);
    }

    public void updateOriginalPosition() {
        originalX = placeholder.getX();
        originalY = placeholder.getY();
    }

    public Actor getPlaceholder() {
        return placeholder;
    }

    public Actor getActor() {
        return actor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ActorWrapper)) return false;
        return actor == ((ActorWrapper) obj).actor;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(actor);
    }

    public void setTargetPosition(float newX, float newY) {
        this.newX = newX;
        this.newY = newY;
    }

    public float getOriginalX() {
        return originalX;
    }

    public float getOriginalY() {
        return originalY;
    }

    public float getNewX() {
        return newX;
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public float getNewY() {
        return newY;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }
}
