package com.libgdxdesign.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.libgdxdesign.core.command.DeleteActorCommand;
import com.libgdxdesign.core.event.RedoEvent;
import com.libgdxdesign.core.event.UndoEvent;

public class HotKeyListener extends InputAdapter {

	private EventBus eventBus;

	@Inject
	public HotKeyListener(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
			switch (keycode) {
			case Keys.Z:
				eventBus.post(new UndoEvent());
				break;
			case Keys.Y:
				eventBus.post(new RedoEvent());
				break;
			default:
				break;
			}
		}

		switch (keycode) {
		case Keys.DEL:
		case Keys.FORWARD_DEL:
			eventBus.post(new DeleteActorCommand());
			break;
		default:
			break;
		}
		return true;
	}

}
