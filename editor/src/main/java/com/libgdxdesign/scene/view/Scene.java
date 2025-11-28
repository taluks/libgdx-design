package com.libgdxdesign.scene.view;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.libgdxdesign.component.loader.TableUtils;

import java.util.ArrayDeque;
import java.util.Deque;

public class Scene extends Stage {

    private final Group originals = new Group();
    private final Group placeholders = new Group();
    private final ObjectMap<Actor, Actor> actors = new ObjectMap<>();

    private final SelectionTracker selectionTracker = new SelectionTracker();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private final Deque<Group> openedGroups = new ArrayDeque<>();

    public Scene() {
        super(new ScreenViewport());

        addActor(new GridRenderer(this));
        //addActor(new RulersRenderer(this));

        originals.setTouchable(Touchable.disabled);
        originals.setName("scene");

        addActor(originals);
        addActor(placeholders);

        shapeRenderer.setAutoShapeType(true);
    }

    @Override
    public void draw() {
        super.draw();
        selectionTracker.render(getViewport(), shapeRenderer);
    }

    public void openGroup(Group group) {
        openedGroups.push(group);
        rebuildPlaceholders();
    }

    public void closeGroup() {
        if (!openedGroups.isEmpty()) {
            openedGroups.pop();
            rebuildPlaceholders();
        }
    }

    public Group getCurrentGroup() {
        return openedGroups.isEmpty() ? originals : openedGroups.peek();
    }

    public void rebuildPlaceholders() {
        placeholders.clearChildren();
        actors.clear();

        Group current = getCurrentGroup();
        if (current == null) return;

        for (Actor child : current.getChildren()) {
            if (current instanceof Table && TableUtils.hasCellFor(child))
                continue;

            Vector2 coordinates = current.localToStageCoordinates(new Vector2(child.getX(), child.getY()));
            ActorPlaceholder placeholder = ActorPlaceholder.from(child, coordinates.x, coordinates.y);
            placeholders.addActor(placeholder);
            actors.put(placeholder, child);
        }

        if (current instanceof Table table) {
            table.validate();
            for (Cell<?> cell : table.getCells()) {
                Actor actor = cell.getActor();
                if (actor == null) throw new RuntimeException("Actor cell is null");

                Vector2 stagePos = current.localToStageCoordinates(new Vector2(actor.getX(), actor.getY()));
                ActorPlaceholder placeholder = ActorPlaceholder.from(actor, stagePos.x, stagePos.y);
                placeholder.setPosition(stagePos.x, stagePos.y);

                placeholders.addActor(placeholder);
                actors.put(placeholder, actor);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void addActorToGroup(Actor actor) {
        if (getCurrentGroup() instanceof Container container) container.setActor(actor);
        else getCurrentGroup().addActor(actor);
    }

    public Actor getOriginal(Actor actor) {
        return actors.get(actor);
    }

    public Group getOriginals() {
        return originals;
    }

    public Group getPlaceholders() {
        return placeholders;
    }

    public SelectionTracker getSelectionTracker() {
        return selectionTracker;
    }
}
