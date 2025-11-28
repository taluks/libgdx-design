package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.model.ActorWrapper;
import com.libgdxdesign.core.model.Project;
import com.libgdxdesign.core.proxy.ProjectProxy;
import com.libgdxdesign.scene.mediator.SceneMediator;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class UiLoadedCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {

        ProjectProxy projectProxy = (ProjectProxy) facade.retrieveProxy(ProjectProxy.NAME);
        Project project = projectProxy.getProject();
        SceneMediator scene = (SceneMediator) facade.retrieveMediator(SceneMediator.NAME);

        ObjectMap<String, Actor> componentActors = project.buildUiComponent();
        for (Actor actor : componentActors.values()) {
            scene.getViewComponent().addActorToGroup(actor);
            Array<Actor> children = findActors(actor);
            for (Actor child : children) {
                facade.sendNotification(EditorNotification.ACTOR_ADDED, ActorWrapper.of(null, child));
            }
        }
    }

    private Array<Actor> findActors(Actor actor) {
        Array<Actor> actors = new Array<>();
        if (actor.getName() != null) {
            actors.add(actor);
            if (actor instanceof Group group) {
                group.getChildren().forEach(a -> actors.addAll(findActors(a)));
            }
        }
        return actors;
    }
}
