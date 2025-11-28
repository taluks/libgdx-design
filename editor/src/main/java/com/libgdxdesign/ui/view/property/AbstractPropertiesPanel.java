package com.libgdxdesign.ui.view.property;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.libgdxdesign.component.loader.ReflectionUtils;
import com.libgdxdesign.ui.event.ActionEvent;
import com.libgdxdesign.ui.view.library.ItemNode;

public abstract class AbstractPropertiesPanel<T extends Actor> extends VerticalGroup {

    public enum Action {
        CENTER,
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,

        PAD_RIGHT,
        PAD_LEFT,
        PAD_TOP,
        PAD_BOTTOM,

        X,
        Y,
        Z,
        WIDTH,
        HEIGHT,

        NAME,
        WINDOW_TITLE,

        LABEL_TEXT,
        LABEL_TEXT_ALIGN,
        LABEL_TEXT_COLOR
    }

    private final Tree tree;
    protected final Node root;

    public AbstractPropertiesPanel(String title, Skin skin) {
        tree = new Tree(skin);
        addActor(tree);
        root = new ItemNode(new Label(title, skin));
        tree.add(root);

        root.expandAll();
        root.setSelectable(false);
    }

    protected void fireActionFloat(Action action, TextField textField) {
        try {
            if (textField.getText().isBlank()) textField.setText("0.0");
            float value = Float.parseFloat(textField.getText());
            fire(new ActionEvent<>(action, value));
        } catch (NumberFormatException e) {
            Gdx.app.log(this.getClass().getSimpleName(), "Number format exeption");
        }
    }

    protected void fireActionInt(Action action, TextField textField) {
        try {
            if (textField.getText().isBlank()) textField.setText("0");
            int value = Integer.parseInt(textField.getText());
            fire(new ActionEvent<>(action, value));
        } catch (NumberFormatException e) {
            Gdx.app.log(this.getClass().getSimpleName(), "Number format exeption");
        }
    }

    public boolean canShowFor(Actor actor) {
        return getActorClass().isAssignableFrom(actor.getClass());
    }

    public Class<?> getActorClass() {
        return ReflectionUtils.getGenericParameterClass(this.getClass(), 0);
    }

    public abstract void update(Actor actor);
}