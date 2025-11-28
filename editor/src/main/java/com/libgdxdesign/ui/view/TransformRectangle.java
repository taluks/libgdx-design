package com.libgdxdesign.ui.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.libgdxdesign.core.model.ActorWrapper;
import com.libgdxdesign.core.model.TransformData;
import com.libgdxdesign.scene.view.PixelRect;
import com.libgdxdesign.utils.TextureUtils;

import java.util.Collections;
import java.util.List;

public class TransformRectangle extends Group {

    public interface TransformItemsListener {
        void onTransform(List<ActorWrapper> actors, float newX, float newY, float newWidth, float newHeight);
    }

    public static final int LT = 0;
    public static final int T = 1;
    public static final int RT = 2;
    public static final int R = 3;
    public static final int RB = 4;
    public static final int B = 5;
    public static final int LB = 6;
    public static final int L = 7;

    private final Group transformGroup = new Group();
    private final Image[] miniRects = new Image[8];

    private final Vector2 originalPosition = new Vector2();
    private final Vector2 originalSize = new Vector2();
    private final Vector2 transformPosition = new Vector2();
    private final Vector2 transformSize = new Vector2();
    private final PixelRect pixelRect = new PixelRect();
    private final Vector2 tmpVector = new Vector2();
    private List<ActorWrapper> actors = Collections.emptyList();
    private TransformItemsListener listener;

    public TransformRectangle() {
        initTransformGroup();
        addActor(pixelRect);
        addActor(transformGroup);

        pixelRect.setTouchable(Touchable.disabled);
        this.addCaptureListener(new InputListenerExtension());
    }

    private void applyTransform(float x, float y, float width, float height) {
        transformPosition.set(x, y);
        transformSize.set(width, height);

        pixelRect.setPosition(originalPosition.x + x, originalPosition.y + y);
        pixelRect.setWidth(originalSize.x + width);
        pixelRect.setHeight(originalSize.y + height);

        positionTransformables();
    }

    private void applyTransform(int rectIndex, float offsetX, float offsetY) {
        switch (rectIndex) {
            case L -> applyTransform(offsetX, 0, -offsetX, 0);
            case LT -> applyTransform(offsetX, 0, -offsetX, offsetY);
            case LB -> applyTransform(offsetX, offsetY, -offsetX, -offsetY);
            case RB -> applyTransform(0, offsetY, offsetX, -offsetY);
            case T -> applyTransform(0, 0, 0, offsetY);
            case B -> applyTransform(0, offsetY, 0, -offsetY);
            case R -> applyTransform(0, 0, offsetX, 0);
            default -> applyTransform(0, 0, offsetX, offsetY);
        }
    }

    private void updateTransform() {
        float finalX = originalPosition.x + transformPosition.x;
        float finalY = originalPosition.y + transformPosition.y;
        float finalWidth = originalSize.x + transformSize.x;
        float finalHeight = originalSize.y + transformSize.y;

        if (listener != null && !actors.isEmpty()) {
            Vector2 finalPosition = new Vector2(originalPosition).add(transformPosition);
            Vector2 finalSize = new Vector2(originalSize).add(transformSize);
            listener.onTransform(actors, finalPosition.x, finalPosition.y, finalSize.x, finalSize.y);
        }

        transformPosition.setZero();
        transformSize.setZero();

        originalPosition.set(finalX, finalY);
        originalSize.set(finalWidth, finalHeight);

        update();
    }

    private void initTransformGroup() {
        for (int i = 0; i < 8; i++) {
            miniRects[i] = getMiniRect();
        }
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

    public void setActors(List<ActorWrapper> actors) {
        this.actors = actors;
        calculateGroupBounds();
        update();
    }

    private void calculateGroupBounds() {
        if (actors.isEmpty()) {
            originalPosition.set(0, 0);
            originalSize.set(0, 0);
            return;
        }

        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;

        for (ActorWrapper aw : actors) {
            Actor actor = aw.getActor();
            if (!actor.hasParent()) continue;

            // Получаем мировые координаты левого нижнего угла актора
            tmpVector.set(actor.getX(), actor.getY());
            Actor parent = actor.getParent();
            parent.localToStageCoordinates(tmpVector);

            float actorWidth = getActorWidth(actor);
            float actorHeight = getActorHeight(actor);

            // Получаем мировые координаты правого верхнего угла актора
            Vector2 topRight = new Vector2(actor.getX() + actorWidth, actor.getY() + actorHeight);
            parent.localToStageCoordinates(topRight);

            minX = Math.min(minX, tmpVector.x);
            minY = Math.min(minY, tmpVector.y);
            maxX = Math.max(maxX, topRight.x);
            maxY = Math.max(maxY, topRight.y);
        }

        originalPosition.set(minX, minY);
        originalSize.set(maxX - minX, maxY - minY);
    }

    private float getActorWidth(Actor actor) {
        return actor.getWidth() * (actor instanceof Label ? 1 : actor.getScaleX());
    }

    private float getActorHeight(Actor actor) {
        return actor.getHeight() * (actor instanceof Label ? 1 : actor.getScaleY());
    }

    public void clear() {
        actors = Collections.emptyList();
        originalPosition.set(0, 0);
        originalSize.set(0, 0);
        update();
    }

    public void update() {
        if (actors.isEmpty()) {
            pixelRect.setPosition(0, 0);
            pixelRect.setWidth(0);
            pixelRect.setHeight(0);
            // Скрываем мини-прямоугольники при очистке
            for (Image rect : miniRects) {
                rect.setVisible(false);
            }
            return;
        }

        calculateGroupBounds();

        // Устанавливаем позицию и размер рамки в stage coordinates
        pixelRect.setPosition(originalPosition.x, originalPosition.y);
        pixelRect.setWidth(originalSize.x);
        pixelRect.setHeight(originalSize.y);

        // Показываем мини-прямоугольники при наличии акторов
        for (Image rect : miniRects) {
            rect.setVisible(true);
        }

        positionTransformables();
    }

    public TransformData getTransformData() {
        return new TransformData(
                actors,
                new Vector2(originalPosition).add(transformPosition),
                new Vector2(originalSize).add(transformSize),
                new Vector2(originalPosition),
                new Vector2(originalSize)
        );
    }

    public List<ActorWrapper> getActors() {
        return actors;
    }

    private Image getMiniRect() {
        Image rect = new Image(TextureUtils.createTexture());
        rect.setScale(6);
        transformGroup.addActor(rect);
        return rect;
    }

    public void setListener(TransformItemsListener listener) {
        this.listener = listener;
    }

    private final class InputListenerExtension extends InputListener {
        private final Vector2 lastPosition = new Vector2();
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