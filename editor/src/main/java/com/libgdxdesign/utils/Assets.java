package com.libgdxdesign.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.inject.Singleton;
import com.libgdxdesign.component.UIComponent;
import com.libgdxdesign.component.loader.UIComponentLoader;

@Singleton
public class Assets extends AssetManager {

	public static final String UI_SKIN_PATH = "skin.json";

	private Skin skin;

	public Assets() {

	}

	public Assets(FileHandleResolver resolver, boolean defaultLoaders) {
		super(resolver, defaultLoaders);
	}

	public Assets(FileHandleResolver resolver) {
		super(resolver);
	}

	public void load() {
		
		this.setLoader(UIComponent.class, new UIComponentLoader(getFileHandleResolver()));
		
		this.load(UI_SKIN_PATH, Skin.class);
		this.finishLoading();
		this.skin = this.get(UI_SKIN_PATH);
		
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture pixel = new Texture(pixmap);
        
        this.skin.add("pixel", pixel);
	}

	public Skin getSkin() {
		return skin;
	}

}
