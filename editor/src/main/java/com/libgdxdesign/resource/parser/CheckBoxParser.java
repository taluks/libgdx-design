package com.libgdxdesign.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.resource.CloneableCheckBox;
import com.libgdxdesign.resource.CloneableResource;

public class CheckBoxParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, CheckBoxStyle> styles = skin.getAll(CheckBoxStyle.class);
		if(styles == null) return items;
		for (String key : styles.keys()) {
			CloneableResource<?> cloneableResource = new CloneableCheckBox(new CheckBox("CheckBox", skin, key));
			items.put(key, cloneableResource);
		}
		return items;
	}

}
