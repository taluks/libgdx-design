package com.libgdxdesign.core.resource.parser;

import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.core.resource.CloneableResource;

public interface ResourceParser {

	Map<String, CloneableResource<?>> getResources(Skin skin);
}
