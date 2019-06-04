package com.libgdxdesign.core.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.core.resource.CloneableResource;
import com.libgdxdesign.core.resource.CloneableTouchpad;

public class TouchpadParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, TouchpadStyle> styles = skin.getAll(TouchpadStyle.class);
		if(styles == null) return items;
		for (String key : styles.keys()) {
			CloneableResource<?> cloneableResource = new CloneableTouchpad(new Touchpad(40, skin, key), 40);
			items.put(key, cloneableResource);
		}
		return items;
	}

}
