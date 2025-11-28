package com.libgdxdesign.ui.view.property;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.libgdxdesign.core.util.NumberTextFieldFilter;
import com.libgdxdesign.ui.event.ActionEvent;
import com.libgdxdesign.ui.view.library.ItemNode;

import java.awt.event.FocusEvent;

public class BaseProperties extends AbstractPropertiesPanel<Actor> {

    public TextField textFieldName;
    public TextField textFieldX;
    public TextField textFieldY;
    public TextField textFieldWidth;
    public TextField textFieldHeight;
    public TextField textFieldZ;

    public BaseProperties(Skin skin) {
        super("Base properties", skin);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(6);
        verticalGroup.align(Align.left);

        Table name = new Table();
        textFieldName = new TextField("", skin);
        Label label = new Label("Name:", skin);
        label.setAlignment(Align.right);
        name.add(label).width(30).padRight(6);
        name.add(textFieldName).width(120).padRight(6);
        textFieldName.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused) {
                    String name = textFieldName.getText();
                    if (!name.isBlank()) fire(new ActionEvent<>(Action.NAME, name));
                }
            }
        });
        verticalGroup.addActor(name);

        Table position = new Table();

        textFieldX = new TextField("0.0", skin);
        textFieldX.setMaxLength(6);
        label = new Label("X:", skin);
        label.setAlignment(Align.right);
        position.add(label).width(25).padRight(6);
        position.add(textFieldX).width(50).padRight(6);
        textFieldX.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused)
                    fireActionFloat(Action.X, textFieldX);
            }
        });
        textFieldX.setTextFieldFilter(new NumberTextFieldFilter());

        textFieldY = new TextField("0.0", skin);
        textFieldY.setMaxLength(6);
        label = new Label("Y:", skin);
        label.setAlignment(Align.right);
        position.add(label).width(25).padRight(6);
        position.add(textFieldY).width(50).padRight(6).padBottom(6).row();
        textFieldY.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused)
                    fireActionFloat(Action.Y, textFieldY);
            }
        });
        textFieldY.setTextFieldFilter(new NumberTextFieldFilter());

        textFieldZ = new TextField("0.0", skin);
        textFieldZ.setMaxLength(6);
        label = new Label("Z:", skin);
        label.setAlignment(Align.right);
        position.add(label).width(25).padRight(6);
        position.add(textFieldZ).width(50).padRight(6);
        textFieldZ.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused)
                    fireActionInt(Action.Z, textFieldZ);
            }
        });
        textFieldZ.setTextFieldFilter(new NumberTextFieldFilter());

        verticalGroup.addActor(position);

        Table size = new Table();
        textFieldWidth = new TextField("0.0", skin);
        textFieldWidth.setMaxLength(6);
        label = new Label("W:", skin);
        label.setAlignment(Align.right);
        size.add(label).width(25).padRight(6);
        size.add(textFieldWidth).width(50).padRight(6);
        textFieldWidth.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused)
                    fireActionFloat(Action.WIDTH, textFieldWidth);
            }
        });
        textFieldWidth.setTextFieldFilter(new NumberTextFieldFilter());

        textFieldHeight = new TextField("0.0", skin);
        textFieldHeight.setMaxLength(6);
        label = new Label("H:", skin);
        label.setAlignment(Align.right);
        size.add(label).width(25).padRight(6);
        size.add(textFieldHeight).width(50).padRight(6);
        textFieldHeight.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused)
                    fireActionFloat(Action.HEIGHT, textFieldHeight);
            }
        });
        textFieldHeight.setTextFieldFilter(new NumberTextFieldFilter());

        verticalGroup.addActor(size);

        Node<?, ?, ?> node = new ItemNode(verticalGroup);
        node.setSelectable(false);
        root.add(node);
    }

    @Override
    public void update(Actor actor) {
        textFieldName.setText(actor.getName());
        textFieldX.setText(String.valueOf(actor.getX()));
        textFieldY.setText(String.valueOf(actor.getY()));
        textFieldWidth.setText(String.valueOf(actor.getWidth()));
        textFieldHeight.setText(String.valueOf(actor.getHeight()));
        textFieldZ.setText(String.valueOf(actor.getZIndex()));
    }
}