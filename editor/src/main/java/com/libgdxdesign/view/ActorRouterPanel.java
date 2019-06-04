package com.libgdxdesign.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class ActorRouterPanel extends Table{

	private Label route;

	public ActorRouterPanel(Skin skin) {
		super(skin);

		setBackground(skin.getDrawable("route"));
		defaults().expandX().fillX();
		route = new Label("scene/", skin);
		add(route).padLeft(4).padTop(2);
		pack();
	}

	public void setActor(Actor actor) {
		Actor parent = actor.getParent();
		Array<String> names = new Array<>();
		names.add(actor.getName());
		if (parent != null) {
			names.add(parent.getName());
			while (parent.hasParent()) {
				parent = parent.getParent();
				names.add(parent.getName());
			}
		}
		names.reverse();
		route.setText(names.toString("/"));
	}


}
