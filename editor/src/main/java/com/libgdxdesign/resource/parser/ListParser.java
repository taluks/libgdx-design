package com.libgdxdesign.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.resource.CloneableList;
import com.libgdxdesign.resource.CloneableResource;

public class ListParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, ListStyle> styles = skin.getAll(ListStyle.class);
		if (styles == null)	return items;
		for (String key : styles.keys()) {
			List<String> list = new List<>(skin, key);
			CloneableResource<?> cloneableResource = new CloneableList(list);
			items.put(key, cloneableResource);
		}
		return items;
	}

}
