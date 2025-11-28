package com.libgdxdesign.ui.view.property;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.libgdxdesign.ui.event.ActionEvent;
import com.libgdxdesign.ui.view.library.ItemNode;

public class WindowProperties extends AbstractPropertiesPanel<Window> {

    private final TextField titleField;

    public WindowProperties(Skin skin) {
        super("Window", skin);
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(6);
        verticalGroup.align(Align.left);

        Table textGroup = new Table();
        titleField = new TextField("", skin);
        Label label = new Label("Title:", skin);
        label.setAlignment(Align.right);
        textGroup.add(label).width(25).padRight(6);
        textGroup.add(titleField).width(120).padRight(6);
        titleField.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused)
                    fire(new ActionEvent<>(Action.WINDOW_TITLE, titleField.getText()));
            }
        });
        verticalGroup.addActor(textGroup);

        Node<?, ?, ?> node = new ItemNode(verticalGroup);
        node.setSelectable(false);
        root.add(node);
    }

    @Override
    public void update(Actor actor) {
        if (actor instanceof Window window) {
            titleField.setText(window.getTitleLabel().getText().toString());
        }
    }
}