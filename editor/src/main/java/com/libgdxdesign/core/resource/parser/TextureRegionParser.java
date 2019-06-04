package com.libgdxdesign.core.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.core.resource.CloneableImage;
import com.libgdxdesign.core.resource.CloneableResource;

public class TextureRegionParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, TextureRegion> styles = skin.getAll(TextureRegion.class);
		if (styles == null)
			return items;
		for (String key : styles.keys()) {
			CloneableResource<?> cloneableResource = new CloneableImage(new Image(skin, key));
			items.put(key, cloneableResource);
		}
		return items;
	}

}
