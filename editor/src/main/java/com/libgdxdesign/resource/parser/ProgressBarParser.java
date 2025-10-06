package com.libgdxdesign.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.resource.CloneableProgressBar;
import com.libgdxdesign.resource.CloneableResource;

public class ProgressBarParser implements ResourceParser {

	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, ProgressBarStyle> styles = skin.getAll(ProgressBarStyle.class);
		if (styles == null)	return items;
		for (String key : styles.keys()) {
			CloneableResource<?> cloneableResource = new CloneableProgressBar(
					new ProgressBar(0, 100, 10, false, skin, key));
			items.put(key, cloneableResource);
		}
		return items;
	}

}
