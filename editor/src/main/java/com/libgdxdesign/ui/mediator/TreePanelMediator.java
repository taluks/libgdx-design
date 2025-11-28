package com.libgdxdesign.ui.mediator;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.OrderedSet;
import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorWrapper;
import com.libgdxdesign.ui.view.TreePanel;
import com.libgdxdesign.ui.view.UiStage;
import com.libgdxdesign.component.loader.TableUtils;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import java.util.ArrayList;
import java.util.List;

public class TreePanelMediator extends Mediator {

    public static final String NAME = TreePanelMediator.class.getSimpleName();

    public TreePanelMediator() {
        super(NAME, new TreePanel());
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                DesignNotification.CREATE,

                EditorNotification.ACTOR_ADDED,
                EditorNotification.ACTOR_REMOVED,
                EditorNotification.ACTOR_SELECTED,
                EditorNotification.ACTOR_DESELECTED,
                EditorNotification.OPEN_GROUP_EDIT,
                EditorNotification.CLOSE_GROUP_EDIT,
                EditorNotification.TABLE_CLEAR_CHILDREN
        };
    }

    @Override
    public void handleNotification(INotification notification) {
        UiStageMediator sceneMediator = (UiStageMediator) facade.retrieveMediator(UiStageMediator.NAME);
        UiStage stage = sceneMediator.getViewComponent();

        switch (notification.getName()) {
            case DesignNotification.CREATE -> {
                stage.getLeftTable().add(getViewComponent()).growY().width(200).left();
                Tree tree = getViewComponent().getTree();
                getViewComponent().getTree().addListener(new ChangeListener() {

                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        OrderedSet<Tree.Node> items = tree.getSelection().items();
                        List<Actor> actors = getViewComponent().getSelected();
                        if (items.isEmpty()) return;

                        if (items.size == actors.size()) {
                            if (actors.stream().allMatch(a -> {
                                for (Tree.Node item : items) {
                                    if (item.getValue() == a) return true;
                                }
                                return false;
                            })) return;
                        }
                        actors = new ArrayList<>();
                        for (Tree.Node item : items) {
                            actors.add((Actor) item.getValue());
                        }
                        facade.sendNotification(EditorNotification.ACTOR_SELECTED,
                                actors.stream().map(a -> ActorWrapper.of(null, a)).toList());
                    }
                });
            }
            case EditorNotification.OPEN_GROUP_EDIT -> {

            }
            case EditorNotification.ACTOR_ADDED -> {
                getViewComponent().addActorTree(((ActorWrapper) notification.getBody()).getActor());
            }
            case EditorNotification.ACTOR_REMOVED -> {
                List<ActorWrapper> wrappers = (List<ActorWrapper>) notification.getBody();
                getViewComponent().removeActorsTree(wrappers.stream().map(ActorWrapper::getActor).toList());
            }
            case EditorNotification.ACTOR_SELECTED -> {
                List<ActorWrapper> wrappers = (List<ActorWrapper>) notification.getBody();
                List<Actor> actors = wrappers.stream().map(ActorWrapper::getActor).toList();
                getViewComponent().selectActor(actors);
            }
            case EditorNotification.ACTOR_DESELECTED -> {
                getViewComponent().deselect();
            }
            case EditorNotification.TABLE_CLEAR_CHILDREN -> {
                Actor actor = (Actor) notification.getBody();
                Table table = actor instanceof Table ? (Table) actor : TableUtils.findCell(actor).getTable();
                getViewComponent().removeChildrenTree(table);
            }
        }
    }

    @Override
    public TreePanel getViewComponent() {
        return (TreePanel) super.getViewComponent();
    }
}
