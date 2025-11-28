package com.libgdxdesign.ui.mediator;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.controller.notification.ResourceNotification;
import com.libgdxdesign.resource.*;
import com.libgdxdesign.resource.parser.ResourceParser;
import com.libgdxdesign.resource.parser.SkinParser;
import com.libgdxdesign.ui.view.TabView;
import com.libgdxdesign.ui.view.UiStage;
import com.libgdxdesign.ui.view.library.LibraryPanel;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class LibraryPanelMediator extends Mediator {
    public static final String NAME = LibraryPanelMediator.class.getSimpleName();

    public LibraryPanelMediator() {
        super(NAME, new LibraryPanel());
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                DesignNotification.CREATE,
                ResourceNotification.SKIN_LOADED
        };
    }

    @Override
    public void handleNotification(INotification notification) {
        UiStageMediator sceneMediator = (UiStageMediator) facade.retrieveMediator(UiStageMediator.NAME);
        UiStage stage = sceneMediator.getViewComponent();

        switch (notification.getName()) {
            case DesignNotification.CREATE -> {
                stage.getRightTabbedPane().add(new TabView("Library", getViewComponent()));
            }
            case ResourceNotification.SKIN_LOADED -> {
                Skin skin = (Skin) notification.getBody();
                handleSkinLoaded(skin);
            }
        }
    }

    private void handleSkinLoaded(Skin skin) {
        getViewComponent().addItems(getStdContainers(), "Containers");
        SkinParser skinParser = new SkinParser();
        for (ResourceParser parser : skinParser.getParsers()) {
            List<ResourceItem> items = new ArrayList<>();
            Map<String, CloneableResource<?>> resources = parser.getResources(skin);
            if (resources.entrySet().isEmpty()) {
                continue;
            }
            for (Map.Entry<String, CloneableResource<?>> entry : resources.entrySet()) {
                String name = entry.getKey();
                CloneableResource<?> resource = entry.getValue();
                items.add(newResourceItem(resource, name));
            }
            items.sort(Comparator.comparing(ResourceItem::getStyleName));
            getViewComponent().addItems(items, items.get(0).getResource().getActor().getClass().getSimpleName());

        }
        getViewComponent().expandAll();
    }

    private List<ResourceItem> getStdContainers() {
        int size = 20;
        Table tableItem = new Table();
        tableItem.setSize(size, size);
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.setSize(size, size);
        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.setSize(size, size);
        Container<?> container = new Container<>();
        container.setSize(size, size);
        return List.of(
                newResourceItem(new CloneableTable(tableItem)),
                newResourceItem(new CloneableVerticalGroup(verticalGroup)),
                newResourceItem(new CloneableHorizontalGroup(horizontalGroup)),
                newResourceItem(new CloneableContainer(container))
        );
    }

    private ResourceItem newResourceItem(CloneableResource<?> resource) {
        return newResourceItem(resource, resource.getActor().getClass().getSimpleName());
    }

    private ResourceItem newResourceItem(CloneableResource<?> resource, String name) {
        UiStageMediator sceneMediator = (UiStageMediator) facade.retrieveMediator(UiStageMediator.NAME);
        ResourceItem item = new ResourceItem(resource, name);
        item.setDropTarget(sceneMediator.getViewComponent().getDummyTarget());
        item.setListener((res, actor) ->
                facade.sendNotification(EditorNotification.ADD_ACTOR, new ResourceWrapper(actor))
        );
        return item;
    }

    @Override
    public LibraryPanel getViewComponent() {
        return (LibraryPanel) super.getViewComponent();
    }

}
