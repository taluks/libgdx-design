package com.libgdxdesign.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.common.eventbus.EventBus;
import com.libgdxdesign.core.command.TransformActorCommand;

public class TransformRectangle extends Group {

	public static final int LT = 0;
	public static final int T = 1;
	public static final int RT = 2;
	public static final int R = 3;
	public static final int RB = 4;
	public static final int B = 5;
	public static final int LB = 6;
	public static final int L = 7;

	private final EventBus eventBus;
	private final Skin skin;
	private final Group transformGroup = new Group();
	private final Image[] miniRects = new Image[8];

	private Vector2 actorPosition = new Vector2();
	private Vector2 actorSize = new Vector2();
	private Vector2 transformPosition = new Vector2();
	private Vector2 transformSize = new Vector2();
	private PixelRect pixelRect = new PixelRect();
	private Actor actor;
	private Vector2 tmpVector = new Vector2();

	public TransformRectangle(EventBus eventBus, Skin skin) {
		this.skin = skin;
		this.eventBus = eventBus;

		initTransformGroup();
		addActor(pixelRect);
		addActor(transformGroup);

		pixelRect.setTouchable(Touchable.disabled);

		this.addCaptureListener(new InputListenerExtension());
	}

	private void applyTransform(float x, float y, float width, float height) {

		transformPosition.set(x, y);
		transformSize.set(width, height);		

		actor.setPosition(actorPosition.x + x, actorPosition.y + y);
		actor.setSize(actorSize.x + width, actorSize.y + height);
		
		actor.getParent().localToStageCoordinates(tmpVector.set(actor.getX(), actor.getY()));
		pixelRect.setPosition(tmpVector.x, tmpVector.y);
		pixelRect.setWidth(actorSize.x + width);
		pixelRect.setHeight(actorSize.y + height);

		positionTransformables();
	}

	private void applyTransform(int rectIndex, float offsetX, float offsetY) {
		switch (rectIndex) {
		case L:
			applyTransform(offsetX, 0, -offsetX, 0);
			break;
		case LT:
			applyTransform(offsetX, 0, -offsetX, offsetY);
			break;
		case LB:
			applyTransform(offsetX, offsetY, -offsetX, -offsetY);
			break;
		case RB:
			applyTransform(0, offsetY, offsetX, -offsetY);
			break;
		case T:
			applyTransform(0, 0, 0, offsetY);
			break;
		case B:
			applyTransform(0, offsetY, 0, -offsetY);
			break;
		case R:
			applyTransform(0, 0, offsetX, 0);
			break;
		default:
			applyTransform(0, 0, offsetX, offsetY);
			break;
		}
	}

	private void updateTransform() {
		float x = actorPosition.x + transformPosition.x;
		float y = actorPosition.y + transformPosition.y;

		float width = actorSize.x + transformSize.x;
		float height = actorSize.y + transformSize.y;

		transformPosition.setZero();
		transformSize.setZero();

		actor.setPosition(actorPosition.x, actorPosition.y);
		actor.setSize(actorSize.x, actorSize.y);

		eventBus.post(new TransformActorCommand(actor, new Vector2(x, y), new Vector2(width, height), actor.getZIndex()));

		actorPosition.set(actor.getX(), actor.getY());
		actorSize.set(actor.getWidth() * (actor instanceof Label ? 1 : actor.getScaleX()),
				actor.getHeight() * (actor instanceof Label ? 1 : actor.getScaleY()));
		update();
	}

	private void initTransformGroup() {
		miniRects[LT] = getMiniRect();
		miniRects[T] = getMiniRect();
		miniRects[RT] = getMiniRect();
		miniRects[R] = getMiniRect();
		miniRects[RB] = getMiniRect();
		miniRects[B] = getMiniRect();
		miniRects[LB] = getMiniRect();
		miniRects[L] = getMiniRect();
	}

	private void positionTransformables() {
		miniRects[LT].setX(pixelRect.getX() - 3);
		miniRects[LT].setY(pixelRect.getY() + pixelRect.getHeight() - 2);
		miniRects[T].setX(pixelRect.getX() + pixelRect.getWidth() / 2 - 3);
		miniRects[T].setY(pixelRect.getY() + pixelRect.getHeight() - 2);
		miniRects[RT].setX(pixelRect.getX() + pixelRect.getWidth() - 3);
		miniRects[RT].setY(pixelRect.getY() + pixelRect.getHeight() - 2);
		miniRects[R].setX(pixelRect.getX() + pixelRect.getWidth() - 3);
		miniRects[R].setY(pixelRect.getY() + pixelRect.getHeight() / 2 - 2);
		miniRects[RB].setX(pixelRect.getX() + pixelRect.getWidth() - 3);
		miniRects[RB].setY(pixelRect.getY() - 2);
		miniRects[B].setX(pixelRect.getX() + pixelRect.getWidth() / 2 - 3);
		miniRects[B].setY(pixelRect.getY() - 2);
		miniRects[LB].setX(pixelRect.getX() - 3);
		miniRects[LB].setY(pixelRect.getY() - 2);
		miniRects[L].setX(pixelRect.getX() - 3);
		miniRects[L].setY(pixelRect.getY() + pixelRect.getHeight() / 2 - 2);
	}

	public void setActor(Actor actor) {

		this.actor = actor;
		actorPosition.set(actor.getX(), actor.getY());
		actorSize.set(actor.getWidth() * (actor instanceof Label ? 1 : actor.getScaleX()),
				actor.getHeight() * (actor instanceof Label ? 1 : actor.getScaleY()));
		update();
	}

	public Actor getActor() {
		return actor;
	}

	public void clear() {
		if (this.actor != null) {
			this.actor = null;
		}
	}

	public void update() {

		actor.getParent().localToStageCoordinates(tmpVector.set(actor.getX(), actor.getY()));
		pixelRect.setPosition(tmpVector.x, tmpVector.y);
		pixelRect.setRotation(actor.getRotation());
		pixelRect.setWidth(actor.getWidth() * (actor instanceof Label ? 1 : actor.getScaleX()));
		pixelRect.setHeight(actor.getHeight() * (actor instanceof Label ? 1 : actor.getScaleY()));

		positionTransformables();
	}

	private Image getMiniRect() {
		Image rect = new Image(skin.newDrawable("pixel"));
		rect.setScale(6);
		rect.setColor(Color.WHITE);

		transformGroup.addActor(rect);
		return rect;
	}

	private final class InputListenerExtension extends InputListener {
		private Vector2 lastPosition = new Vector2();
		private int currentRectIndex;

		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

			for (int i = 0; i < 8; i++) {
				currentRectIndex = i;
				Image minRect = miniRects[i];
				if (minRect == event.getTarget()) {
					lastPosition.set(minRect.getX(), minRect.getY());
					return true;
				}
			}
			return false;
		}

		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

			updateTransform();
			lastPosition.setZero();
			super.touchUp(event, x, y, pointer, button);
		}

		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer) {
			applyTransform(currentRectIndex, x - lastPosition.x, y - lastPosition.y);
			super.touchDragged(event, x, y, pointer);
		}
	}

}
