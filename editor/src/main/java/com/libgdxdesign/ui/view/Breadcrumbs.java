package com.libgdxdesign.ui.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;

public class Breadcrumbs extends Table {

    private final Label route;
    public Breadcrumbs() {
        setBackground(VisUI.getSkin().getDrawable("grey"));
        defaults().expandX().fillX();
        route = new VisLabel();
        add(route).pad(4);
    }

    public void setActor(Actor actor) {
        if (actor.getName() == null) {
            route.setText("scene");
            return;
        }

        Array<String> names = new Array<>();
        names.add(actor.getName());
        Actor current = actor;
        while (current.hasParent()) {
            current = current.getParent();
            if (current.getName() != null)
                names.add(current.getName());
        }
        names.reverse();
        route.setText(names.toString(" > "));
    }
}
