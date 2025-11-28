package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorWrapper;
import com.libgdxdesign.core.proxy.SelectionProxy;
import com.libgdxdesign.scene.mediator.SceneMediator;
import com.libgdxdesign.scene.view.TableCell;
import com.libgdxdesign.component.loader.TableUtils;

import java.util.List;

public class RemoveActorCommand extends RevertibleCommand {

    @Override
    public void doAction() {
        SelectionProxy selectionProxy = (SelectionProxy) facade.retrieveProxy(SelectionProxy.NAME);
        selectionProxy.setSelection(null);

        List<ActorWrapper> list = (List<ActorWrapper>) body;
        SceneMediator scene = (SceneMediator) facade.retrieveMediator(SceneMediator.NAME);
        list.forEach(actorWrapper -> {
            Cell cell = TableUtils.findCell(actorWrapper.getActor());
            if (cell != null) {
                cell.setActor(new TableCell());
            }
            actorWrapper.getActor().remove();

        });
        facade.sendNotification(EditorNotification.ACTOR_REMOVED, list);

        facade.sendNotification(EditorNotification.ACTOR_UPDATED);
    }

    @Override
    public void undoAction() {

    }
}
