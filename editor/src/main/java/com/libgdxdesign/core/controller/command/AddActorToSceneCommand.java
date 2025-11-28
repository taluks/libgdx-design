package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorWrapper;
import com.libgdxdesign.core.proxy.SelectionProxy;
import com.libgdxdesign.resource.ResourceWrapper;
import com.libgdxdesign.scene.mediator.SceneMediator;

import java.util.Collections;
import java.util.List;

import static com.libgdxdesign.component.loader.TableUtils.findCell;

public class AddActorToSceneCommand extends RevertibleCommand {

    private static int counter = 1;

    @Override
    public void doAction() {
        ResourceWrapper wrapper = (ResourceWrapper) body;
        Actor actor = wrapper.getActor();

        actor.setName(actor.getClass().getSimpleName() + counter++);
        SceneMediator scene = (SceneMediator) facade.retrieveMediator(SceneMediator.NAME);
        scene.getViewComponent().addActorToGroup(actor);

        // Table cell
        SelectionProxy selectionProxy = (SelectionProxy) facade.retrieveProxy(SelectionProxy.NAME);
        List<ActorWrapper> selectedActors = selectionProxy.getSelectedActors();
        if (selectedActors.size() == 1) {
            ActorWrapper selected = selectedActors.get(0);
            Cell cell =  findCell(selected.getActor());
            if (cell != null)  {
                facade.sendNotification(EditorNotification.REMOVE_ACTOR, Collections.singletonList(selected));
                cell.setActor(actor);
                ((Table)actor.getParent()).invalidateHierarchy();
            }
        }

        Vector2 tmpVector = new Vector2();
        actor.getParent().stageToLocalCoordinates(tmpVector.set(actor.getX(), actor.getY()));
        actor.setPosition(tmpVector.x, tmpVector.y);

        facade.sendNotification(EditorNotification.ACTOR_ADDED, ActorWrapper.of(null, actor));
    }

    @Override
    public void undoAction() {
        ResourceWrapper wrapper = (ResourceWrapper) body;
        wrapper.getActor().remove();
    }
}
