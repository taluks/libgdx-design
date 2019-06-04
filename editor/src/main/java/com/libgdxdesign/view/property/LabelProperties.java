package com.libgdxdesign.view.property;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.google.common.eventbus.EventBus;
import com.libgdxdesign.core.command.UpdateLabelCommand;

public class LabelProperties extends AbstractPropertiesPanel<Label> {

	// TODO ...
	private static final int[] ALIGN_INDEXES = { Align.right, Align.center, Align.left };

	private EventBus eventBus;
	private SelectBox<String> selectAlignBox;

	private TextField textField;
	private Label currentLabel;
	private TextField colorField;

	public LabelProperties(Skin skin, EventBus eventBus) {
		super("Character", skin);
		this.eventBus = eventBus;

		VerticalGroup verticalGroup = new VerticalGroup();
		verticalGroup.space(6);

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
		selectAlignBox.addListener(new ChangeListenerExtension());
		verticalGroup.addActor(alignGroup);

		Table textGroup = new Table();
		textField = new TextField("", skin);
		label = new Label("Text:", skin);
		label.setAlignment(Align.right);
		textGroup.add(label).width(25).padRight(6);
		textGroup.add(textField).width(120).padRight(6);
		textField.addListener(new ChangeListenerExtension());
		verticalGroup.addActor(textGroup);

		Table colorGroup = new Table();
		colorField = new TextField(Color.WHITE.toString(), skin);
		colorField.setDisabled(true);
		label = new Label("Color:", skin);
		label.setAlignment(Align.right);
		colorGroup.add(label).width(25).padRight(6);
		colorGroup.add(colorField).width(120).padRight(6);
		verticalGroup.addActor(colorGroup);

		Node node = new Node(verticalGroup);
		node.setSelectable(false);
		root.add(node);
	}

	private final class ChangeListenerExtension extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			updateLabel();
		}
	}

	private void updateLabel() {
		int align = ALIGN_INDEXES[selectAlignBox.getSelectedIndex()];
		String text = textField.getText();

		eventBus.post(new UpdateLabelCommand(currentLabel, text, align));
	}

	@Override
	public void update(Actor actor) {
		if (actor instanceof Label) {
			currentLabel = (Label) actor;
			textField.setText(currentLabel.getText().toString());
			selectAlignBox.setSelected(Align.toString(currentLabel.getLabelAlign()));
			// selectFontBox.setSelected(label.getStyle().font.toString());
			if (currentLabel.getStyle().fontColor != null)
				colorField.setText(currentLabel.getStyle().fontColor.toString());
		}

	}

}
