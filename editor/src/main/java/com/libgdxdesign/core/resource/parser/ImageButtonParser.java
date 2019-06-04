package com.libgdxdesign.core.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.core.resource.CloneableImageButton;
import com.libgdxdesign.core.resource.CloneableResource;

public class ImageButtonParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, ImageButtonStyle> styles = skin.getAll(ImageButtonStyle.class);
		if(styles == null) return items;
		for(String key: styles.keys()) {
			CloneableResource<?> cloneableResource = new CloneableImageButton(new ImageButton(skin, key));
			items.put(key, cloneableResource);
		}
		return items;
	}

}
