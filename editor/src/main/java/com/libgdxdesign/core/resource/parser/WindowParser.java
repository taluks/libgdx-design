package com.libgdxdesign.core.resource.parser;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.core.resource.CloaneableWindow;
import com.libgdxdesign.core.resource.CloneableResource;

public class WindowParser implements ResourceParser {


	@Override
	public Map<String, CloneableResource<?>> getResources(Skin skin) {
		Map<String, CloneableResource<?>> items = new HashMap<>();
		ObjectMap<String, WindowStyle> styles = skin.getAll(WindowStyle.class);
		if(styles == null) return items;
		for (String key : styles.keys()) {
			WindowStyle windowStyle = styles.get(key);
			windowStyle.stageBackground = null;
			Window window = new Window("Window", skin, key);
			CloneableResource<?> cloneableResource = new CloaneableWindow(window);
			items.put(key, cloneableResource);
		}
		return items;
	}

}
