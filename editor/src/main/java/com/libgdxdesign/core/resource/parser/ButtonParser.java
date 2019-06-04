package com.libgdxdesign.core.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.core.resource.CloneableButton;
import com.libgdxdesign.core.resource.CloneableResource;

public class ButtonParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, ButtonStyle> styles = skin.getAll(ButtonStyle.class);
		if(styles == null) return items;
		for(String key: styles.keys()) {
			CloneableResource<?> cloneableResource = new CloneableButton(new Button(skin, key));
			items.put(key, cloneableResource);
		}
		return items;
	}

}
