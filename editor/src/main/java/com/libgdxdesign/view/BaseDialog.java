package com.libgdxdesign.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public abstract class BaseDialog extends Dialog {

	public BaseDialog(String title, Skin skin) {
		super(title, skin);

		ImageButton closeButton = new ImageButton(skin, "close-btn");
		this.getTitleTable().add(closeButton).padBottom(2);
		closeButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				remove();
				
			}
		});
	}

}
