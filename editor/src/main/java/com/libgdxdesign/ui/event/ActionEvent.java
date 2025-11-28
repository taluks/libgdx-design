package com.libgdxdesign.ui.event;

import com.badlogic.gdx.scenes.scene2d.Event;

public class ActionEvent<T>  extends Event {
    private final T action;
    private final Object data;

    public ActionEvent(T action) {
        this(action, null);
    }

    public ActionEvent(T action, Object data) {
        this.action = action;
        this.data = data;
    }

    public T getAction() {
        return action;
    }

    public Object getData() {
        return data;
    }
}
