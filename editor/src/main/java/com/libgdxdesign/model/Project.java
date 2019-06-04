package com.libgdxdesign.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.component.UIComponent;

public class Project {

	private Skin skin;
	private UIComponent uiComponent;

	public Project() {

	}

	public ObjectMap<String, Actor> builUiComponent() {
		return uiComponent.build(skin);
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public UIComponent getUiComponent() {
		return uiComponent;
	}

	public void setUiComponent(UIComponent uiComponent) {
		this.uiComponent = uiComponent;
	}

}
