package com.libgdxdesign.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.resource.CloneableResource;
import com.libgdxdesign.resource.CloneableTextButton;

public class TextButtonParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, TextButtonStyle> styles = skin.getAll(TextButtonStyle.class);
		if (styles == null)
			return items;
		for (String key : styles.keys()) {
			CloneableResource<?> cloneableResource = new CloneableTextButton(new TextButton("Button", skin, key));
			items.put(key, cloneableResource);
		}
		return items;
	}

}
