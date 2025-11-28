package com.libgdxdesign.ui.view.property;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.libgdxdesign.core.util.NumberTextFieldFilter;
import com.libgdxdesign.ui.event.ActionEvent;
import com.libgdxdesign.ui.view.library.ItemNode;
import com.libgdxdesign.component.loader.TableUtils;

public class CellProperties extends AbstractPropertiesPanel<Actor> {

    private final TextField leftPad;
    private final TextField rightPad;
    private final TextField topPad;
    private final TextField bottomPad;

    public CellProperties(Skin skin) {
        super("Cell", skin);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(6);
        verticalGroup.align(Align.left);

        HorizontalGroup alignBtns = new HorizontalGroup();
        verticalGroup.addActor(alignBtns);

        TextButton leftBtn = new VisTextButton("Left");
        leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new ActionEvent<>(Action.LEFT));
            }
        });
        TextButton rightBtn = new VisTextButton("Right");
        rightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new ActionEvent<>(Action.RIGHT));
            }
        });
        TextButton topBtn = new VisTextButton("Top");
        topBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new ActionEvent<>(Action.TOP));
            }
        });
        TextButton bottomBtn = new VisTextButton("Bottom");
        bottomBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new ActionEvent<>(Action.BOTTOM));
            }
        });
        TextButton centerBtn = new VisTextButton("Center");
        centerBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new ActionEvent<>(Action.CENTER));
            }
        });
        alignBtns.addActor(leftBtn);
        alignBtns.addActor(rightBtn);
        alignBtns.addActor(centerBtn);
        alignBtns.addActor(topBtn);
        alignBtns.addActor(bottomBtn);

        Table pad = new Table();
        pad.defaults().space(6);
        verticalGroup.addActor(pad);

        Label left = new VisLabel("Left:");
        leftPad = new TextField("0.0", skin);
        leftPad.setTextFieldFilter(new NumberTextFieldFilter());
        leftPad.setMaxLength(6);
        leftPad.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused)
                    fireActionFloat(Action.PAD_LEFT, leftPad);
            }
        });

        Label right = new VisLabel("Right:");
        rightPad = new TextField("0.0", skin);
        rightPad.setTextFieldFilter(new NumberTextFieldFilter());
        rightPad.setMaxLength(6);
        rightPad.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused)
                    fireActionFloat(Action.PAD_RIGHT, rightPad);
            }
        });

        Label top = new VisLabel("Top:");
        topPad = new TextField("0.0", skin);
        topPad.setTextFieldFilter(new NumberTextFieldFilter());
        topPad.setMaxLength(6);
        topPad.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused)
                    fireActionFloat(Action.PAD_TOP, topPad);
            }
        });

        Label bottom = new VisLabel("Bottom:");
        bottomPad = new TextField("0.0", skin);
        bottomPad.setTextFieldFilter(new NumberTextFieldFilter());
        bottomPad.setMaxLength(6);
        bottomPad.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused)
                    fireActionFloat(Action.PAD_BOTTOM, bottomPad);
            }
        });

        pad.add(left).left();
        pad.add(leftPad).width(50);
        pad.add(right).left();
        pad.add(rightPad).width(50).row();
        pad.add(top).left();
        pad.add(topPad).width(50);
        pad.add(bottom).left();
        pad.add(bottomPad).width(50);

        Tree.Node<?, ?, ?> node = new ItemNode(verticalGroup);
        node.setSelectable(false);
        root.add(node);
    }

    @Override
    public void update(Actor actor) {
        Cell<?> cell = TableUtils.findCell(actor);
        leftPad.setText(String.valueOf(cell.getPadLeft()));
        rightPad.setText(String.valueOf(cell.getPadRight()));
        topPad.setText(String.valueOf(cell.getPadTop()));
        bottomPad.setText(String.valueOf(cell.getPadBottom()));
    }

    @Override
    public boolean canShowFor(Actor actor) {
        return TableUtils.hasCellFor(actor);
    }
}
