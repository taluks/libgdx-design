package com.libgdxdesign.view.property;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.google.common.eventbus.EventBus;
import com.libgdxdesign.core.command.UpdateWindowCommand;

public class WindowProperties extends AbstractPropertiesPanel<Window> {

	private EventBus eventBus;
	private TextField titleField;
	private Window currentWindow;

	public WindowProperties(Skin skin, EventBus eventBus) {
		super("Window", skin);
		this.eventBus = eventBus;
		VerticalGroup verticalGroup = new VerticalGroup();
		verticalGroup.space(6);

		Table textGroup = new Table();
		titleField = new TextField("", skin);
		Label label = new Label("Title:", skin);
		label.setAlignment(Align.right);
		textGroup.add(label).width(25).padRight(6);
		textGroup.add(titleField).width(120).padRight(6);
		titleField.addListener(new ChangeListenerExtension());
		verticalGroup.addActor(textGroup);

		Node node = new Node(verticalGroup);
		node.setSelectable(false);
		root.add(node);
	}

	@Override
	public void update(Actor actor) {
		if (actor instanceof Window) {
			currentWindow = (Window) actor;
			titleField.setText(currentWindow.getTitleLabel().getText().toString());
		}
	}

	private final class ChangeListenerExtension extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			eventBus.post(new UpdateWindowCommand(currentWindow, titleField.getText().toString()));
		}
	}
}
