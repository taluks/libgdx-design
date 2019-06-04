package com.libgdxdesign.component.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.libgdxdesign.component.UIComponent;

public class UIComponentLoader extends SynchronousAssetLoader<UIComponent, AssetLoaderParameters<UIComponent>> {

	public UIComponentLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public UIComponent load(AssetManager assetManager, String fileName, FileHandle file,
			AssetLoaderParameters<UIComponent> parameter) {		
		return new UIComponent().load(file);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file,
			AssetLoaderParameters<UIComponent> parameter) {		
		return null;
	}
	
}
