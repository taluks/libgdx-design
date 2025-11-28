package com.libgdxdesign.component.loader;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.libgdxdesign.component.model.ActorData;
import com.libgdxdesign.component.model.CellData;

public class CellDataSerializer extends Json.ReadOnlySerializer<CellData> {

    @Override
    public CellData read(Json json, JsonValue jsonData, Class type) {
        CellData cell = new CellData();
        cell.padTop = json.readValue("padTop", float.class, 0f, jsonData);
        cell.padLeft = json.readValue("padLeft", float.class, 0f, jsonData);
        cell.padBottom = json.readValue("padBottom", float.class, 0f, jsonData);
        cell.padRight = json.readValue("padRight", float.class, 0f, jsonData);
        cell.align = json.readValue("align", int.class, 0, jsonData);
        cell.colspan = json.readValue("colspan", int.class, 1, jsonData);
        cell.endRow = json.readValue("endRow", boolean.class, false, jsonData);
        cell.fillX = json.readValue("fillX", float.class, 0f, jsonData);
        cell.fillY = json.readValue("fillY", float.class, 0f, jsonData);
        cell.expandX = json.readValue("expandX", int.class, 0, jsonData);
        cell.expandY = json.readValue("expandY", int.class, 0, jsonData);
        JsonValue actorValue = jsonData.get("actor");
        if (actorValue != null) {
            String className = json.readValue("className", String.class, actorValue);
            cell.actor = json.readValue(ActorData.class, json.getClass(className), actorValue);
        }
        return cell;
    }
}