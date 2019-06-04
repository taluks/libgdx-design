package com.libgdxdesign.core.command;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class UpdateLabelCommand implements Command {

	private final Label label;
	private final String text;
	private final int alignment;
	private final String newText;
	private final int newAlignment;

	public UpdateLabelCommand(Label label, String newText, int newAlignment) {
		this.label = label;
		this.text = label.getText().toString();
		this.alignment = label.getLabelAlign();

		this.newText = newText;
		this.newAlignment = newAlignment;
	}

	@Override
	public void execute(CommandExecutor commandExecutor) {
		label.setText(newText);
		label.setAlignment(newAlignment);
	}

	@Override
	public void undo(CommandExecutor commandExecutor) {
		label.setText(text);
		label.setAlignment(alignment);
	}

}
