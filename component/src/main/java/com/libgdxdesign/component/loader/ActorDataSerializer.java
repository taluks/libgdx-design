package com.libgdxdesign.component.loader;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.libgdxdesign.component.model.ActorData;
import com.badlogic.gdx.utils.JsonValue;

public class ActorDataSerializer extends ReadOnlySerializer<ActorData> {

	@Override
	public ActorData read(Json json, JsonValue jsonData, @SuppressWarnings("rawtypes") Class type) {		
		String className = json.readValue("className", String.class, jsonData);
		Class<?> clazz = json.getClass(className);
		return (ActorData) json.readValue(clazz, jsonData);
	}

}
