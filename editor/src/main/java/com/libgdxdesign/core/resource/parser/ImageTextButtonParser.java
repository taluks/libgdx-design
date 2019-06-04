package com.libgdxdesign.core.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.core.resource.CloneableImageTextButton;
import com.libgdxdesign.core.resource.CloneableResource;

public class ImageTextButtonParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, ImageTextButtonStyle> styles = skin.getAll(ImageTextButtonStyle.class);
		if (styles == null)
			return items;
		for (String key : styles.keys()) {
			CloneableResource<?> cloneableResource = new CloneableImageTextButton(
					new ImageTextButton("Button", skin, key));
			items.put(key, cloneableResource);
		}
		return items;
	}

}
