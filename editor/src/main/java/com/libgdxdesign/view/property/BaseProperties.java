package com.libgdxdesign.view.property;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter.DigitsOnlyFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.google.common.eventbus.EventBus;
import com.libgdxdesign.core.NumberTextFieldFilter;
import com.libgdxdesign.core.command.ChangeActorNameCommand;
import com.libgdxdesign.core.command.TransformActorCommand;

public class BaseProperties extends AbstractPropertiesPanel<Actor> {

	private EventBus eventBus;
	private Actor currentActor;
	public TextField textFieldName;
	public TextField textFieldX;
	public TextField textFieldY;
	public TextField textFieldWidth;
	public TextField textFieldHeight;
	public TextField textFieldZ;

	public BaseProperties(Skin skin, EventBus eventBus) {
		super("Base properties", skin);
		this.eventBus = eventBus;

		VerticalGroup verticalGroup = new VerticalGroup();
		verticalGroup.space(6);

		Table name = new Table();
		textFieldName = new TextField("", skin);
		Label label = new Label("Name:", skin);
		label.setAlignment(Align.right);
		name.add(label).width(30).padRight(6);
		name.add(textFieldName).width(120).padRight(6);
		textFieldName.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				String name = textFieldName.getText();
				if (name != "")
					eventBus.post(new ChangeActorNameCommand(currentActor, name));
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
		textFieldX.addListener(new ChangeListenerExtension());
		textFieldX.setTextFieldFilter(new NumberTextFieldFilter());

		textFieldY = new TextField("0.0", skin);
		textFieldY.setMaxLength(6);
		label = new Label("Y:", skin);
		label.setAlignment(Align.right);
		position.add(label).width(25).padRight(6);
		position.add(textFieldY).width(50).padRight(6).padBottom(6).row();
		textFieldY.addListener(new ChangeListenerExtension());
		textFieldY.setTextFieldFilter(new NumberTextFieldFilter());

		textFieldZ = new TextField("0.0", skin);
		textFieldZ.setMaxLength(6);
		label = new Label("Z:", skin);
		label.setAlignment(Align.right);
		position.add(label).width(25).padRight(6);
		position.add(textFieldZ).width(50).padRight(6);
		textFieldZ.addListener(new ChangeListenerExtension());
		textFieldZ.setTextFieldFilter(new NumberTextFieldFilter());

		verticalGroup.addActor(position);

		Table size = new Table();
		textFieldWidth = new TextField("0.0", skin);
		textFieldWidth.setMaxLength(6);
		label = new Label("W:", skin);
		label.setAlignment(Align.right);
		size.add(label).width(25).padRight(6);
		size.add(textFieldWidth).width(50).padRight(6);
		textFieldWidth.addListener(new ChangeListenerExtension());
		textFieldWidth.setTextFieldFilter(new NumberTextFieldFilter());

		textFieldHeight = new TextField("0.0", skin);
		textFieldHeight.setMaxLength(6);
		label = new Label("H:", skin);
		label.setAlignment(Align.right);
		size.add(label).width(25).padRight(6);
		size.add(textFieldHeight).width(50).padRight(6);
		textFieldHeight.addListener(new ChangeListenerExtension());
		textFieldHeight.setTextFieldFilter(new NumberTextFieldFilter());

		verticalGroup.addActor(size);

		Node node = new Node(verticalGroup);
		node.setSelectable(false);
		root.add(node);
	}

	private final class ChangeListenerExtension extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			updateTransform();
		}
	}

	private void updateTransform() {
		try {
			float newX = Float.parseFloat(textFieldX.getText());
			float newY = Float.parseFloat(textFieldY.getText());
			int newZ = Integer.parseInt(textFieldZ.getText());
			float newWidth = Float.parseFloat(textFieldWidth.getText());
			float newHeight = Float.parseFloat(textFieldHeight.getText());

			TransformActorCommand cmd = new TransformActorCommand(currentActor, new Vector2(newX, newY),
					new Vector2(newWidth, newHeight), newZ);
			cmd.setNeedUpdateEvent(false);
			eventBus.post(cmd);
		} catch (NumberFormatException e) {
			Gdx.app.log(this.getClass().getSimpleName(), "Number format exeption");
		}
	}

	@Override
	public void update(Actor actor) {
		this.currentActor = actor;
		textFieldName.setText(actor.getName());
		textFieldX.setText(String.valueOf(actor.getX()));
		textFieldY.setText(String.valueOf(actor.getY()));
		textFieldWidth.setText(String.valueOf(actor.getWidth()));
		textFieldHeight.setText(String.valueOf(actor.getHeight()));
		textFieldZ.setText(String.valueOf(actor.getZIndex()));
	}

}
