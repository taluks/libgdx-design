package com.libgdxdesign.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.resource.CloneableResource;
import com.libgdxdesign.resource.CloneableSelectBox;

public class SelectBoxParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, SelectBoxStyle> styles = skin.getAll(SelectBoxStyle.class);
		if(styles == null) return items;
		for(String key: styles.keys()) {
			SelectBox<String> selectBox = new SelectBox<>(skin, key);
			selectBox.setItems("item 1", "item 2");
			CloneableResource<?> cloneableResource = new CloneableSelectBox(selectBox);
			items.put(key, cloneableResource);
		}
		return items;
	}

}
