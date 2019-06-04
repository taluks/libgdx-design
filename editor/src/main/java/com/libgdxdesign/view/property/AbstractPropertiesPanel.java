package com.libgdxdesign.view.property;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.libgdxdesign.component.loader.ReflectionUtils;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public abstract class AbstractPropertiesPanel<T extends Actor> extends VerticalGroup {

	private final Tree tree;
	protected final Node root;		
	public AbstractPropertiesPanel(String title, Skin skin) {
		tree = new Tree(skin);	
		addActor(tree);
		root = new Node(new Label(title, skin));
		tree.add(root);
		
		root.expandAll();		
		root.setSelectable(false);		
	}
	
	public Class<?> getActorClass() {
		return ReflectionUtils.getGenericParameterClass(this.getClass(), 0);
	}
	
	
	public abstract void update(Actor actor);
}
