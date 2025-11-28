package com.libgdxdesign.component.loader;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;
import com.libgdxdesign.component.UIComponent;
import com.libgdxdesign.component.model.ActorData;

public class UIComponentSerializer extends ReadOnlySerializer<UIComponent> {

	private final UIComponent component;

	public UIComponentSerializer(UIComponent component) {
		this.component = component;
	}

	@Override
	public UIComponent read(Json json, JsonValue jsonData, Class type) {
		if (jsonData.isArray()) {
			for (JsonValue child : jsonData) {
				component.add(readActorData(json, child));
			}
		} else {
			component.add(readActorData(json, jsonData));
		}
		return component;
	}

	protected ActorData readActorData(Json json, JsonValue jsonData) {
		String className = json.readValue("className", String.class, jsonData);
		return json.readValue(ActorData.class, json.getClass(className), jsonData);
	}
}
