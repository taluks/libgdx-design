package com.libgdxdesign.core.controller.command;

import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorWrapper;
import com.libgdxdesign.core.proxy.SelectionProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда выделения
 */
public class SelectActorCommand extends RevertibleCommand {

    private List<ActorWrapper> previousSelection;
    private List<ActorWrapper> newSelection;

    @SuppressWarnings("unchecked")
    @Override
    public void doAction() {
        SelectionProxy selectionProxy = (SelectionProxy) facade.retrieveProxy(SelectionProxy.NAME);
        previousSelection = new ArrayList<>(selectionProxy.getSelectedActors());
        newSelection = (List<ActorWrapper>) body;
        selectionProxy.setSelection(newSelection);
    }

    @Override
    public void undoAction() {
        SelectionProxy selectionProxy = (SelectionProxy) facade.retrieveProxy(SelectionProxy.NAME);
        selectionProxy.setSelection(previousSelection);
        if (previousSelection != null) facade.sendNotification(EditorNotification.ACTOR_SELECTED, previousSelection);
        else facade.sendNotification(EditorNotification.ACTOR_DESELECTED);
    }
}
