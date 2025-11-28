package com.libgdxdesign.core.proxy;

import com.libgdxdesign.core.model.ActorWrapper;
import org.puremvc.java.patterns.proxy.Proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectionProxy extends Proxy {

    public static final String NAME = SelectionProxy.class.getSimpleName();

    private final List<ActorWrapper> selectedActors = new ArrayList<>();


    public SelectionProxy() {
        super(NAME);
    }

    public List<ActorWrapper> getSelectedActors() {
        return Collections.unmodifiableList(selectedActors);
    }

    public ActorWrapper getPrimarySelection() {
        return selectedActors.isEmpty() ? null : selectedActors.get(0);
    }

    public void setSelection(List<ActorWrapper> newSelection) {
        selectedActors.clear();
        if (newSelection != null) {
            selectedActors.addAll(newSelection);
        }
    }
    public void toggleSelection(ActorWrapper actor) {
        if (selectedActors.contains(actor))
            selectedActors.remove(actor);
        else
            selectedActors.add(actor);
    }
    public void clearSelection() {
        selectedActors.clear();
    }

    public boolean isSelected(ActorWrapper actor) {
        return selectedActors.contains(actor);
    }

    public boolean hasSelection() {
        return !selectedActors.isEmpty();
    }
}
