package com.libgdxdesign.resource.parser;

import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.resource.CloneableResource;

public interface ResourceParser {

	Map<String, CloneableResource<?>> getResources(Skin skin);
}
