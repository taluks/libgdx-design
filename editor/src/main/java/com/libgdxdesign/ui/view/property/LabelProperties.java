package com.libgdxdesign.ui.view.property;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.libgdxdesign.ui.event.ActionEvent;
import com.libgdxdesign.ui.view.library.ItemNode;

public class LabelProperties extends AbstractPropertiesPanel<Label> {

    // TODO ...
    private static final int[] ALIGN_INDEXES = {Align.right, Align.center, Align.left};

    private SelectBox<String> selectAlignBox;

    private TextField textField;
    private TextField colorField;

    public LabelProperties(Skin skin) {
        super("Label", skin);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(6);
        verticalGroup.align(Align.left);

        String[] alignNames = new String[ALIGN_INDEXES.length];
        for (int i = 0; i < ALIGN_INDEXES.length; i++) {
            alignNames[i] = Align.toString(ALIGN_INDEXES[i]);
        }

        Table alignGroup = new Table();
        selectAlignBox = new SelectBox<>(skin);
        selectAlignBox.setItems(alignNames);
        Label label = new Label("Align:", skin);
        label.setAlignment(Align.right);
        alignGroup.add(label).width(25).padRight(6);
        alignGroup.add(selectAlignBox).width(120).padRight(6);
        selectAlignBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int align = ALIGN_INDEXES[selectAlignBox.getSelectedIndex()];
                fire(new ActionEvent<>(Action.LABEL_TEXT_ALIGN, align));
            }
        });
        verticalGroup.addActor(alignGroup);

        Table textGroup = new Table();
        textField = new TextField("", skin);
        label = new Label("Text:", skin);
        label.setAlignment(Align.right);
        textGroup.add(label).width(25).padRight(6);
        textGroup.add(textField).width(120).padRight(6);
        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fire(new ActionEvent<>(Action.LABEL_TEXT, textField.getText()));
            }
        });
        verticalGroup.addActor(textGroup);

        Table colorGroup = new Table();
        colorField = new TextField(Color.WHITE.toString(), skin);
        colorField.setDisabled(true);
        label = new Label("Color:", skin);
        label.setAlignment(Align.right);
        colorGroup.add(label).width(25).padRight(6);
        colorGroup.add(colorField).width(120).padRight(6);
        verticalGroup.addActor(colorGroup);

        Node node = new ItemNode(verticalGroup);
        node.setSelectable(false);
        root.add(node);
    }

    @Override
    public void update(Actor actor) {
        if (actor instanceof Label label) {
            textField.setText(label.getText().toString());
            selectAlignBox.setSelected(Align.toString(label.getLabelAlign()));
            // selectFontBox.setSelected(label.getStyle().font.toString());
            if (label.getStyle().fontColor != null)
                colorField.setText(label.getStyle().fontColor.toString());
        }
    }
}