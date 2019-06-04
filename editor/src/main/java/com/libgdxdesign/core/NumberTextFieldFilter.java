package com.libgdxdesign.core;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;

public class NumberTextFieldFilter implements TextFieldFilter {

	@Override
	public boolean acceptChar(TextField textField, char c) {
		return Character.isDigit(c) || Character.getDirectionality(c) == Character.ENCLOSING_MARK;
	}

}
