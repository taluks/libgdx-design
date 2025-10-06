package com.libgdxdesign.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.resource.CloneableLabel;
import com.libgdxdesign.resource.CloneableResource;

public class LabelParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, LabelStyle> styles = skin.getAll(LabelStyle.class);
		if (styles == null)	return items;
		for (String key : styles.keys()) {
			CloneableResource<?> cloneableResource = new CloneableLabel(new Label("Label", skin, key));
			items.put(key, cloneableResource);
		}
		return items;
	}

}
