package com.libgdxdesign.core.controller.command;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.libgdxdesign.component.UIComponent;
import com.libgdxdesign.component.model.ActorData;
import com.libgdxdesign.core.model.Project;
import com.libgdxdesign.core.proxy.ProjectProxy;
import com.libgdxdesign.scene.mediator.SceneMediator;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class SaveUiComponentCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        String fileName = (String) notification.getBody();
        ProjectProxy projectProxy = (ProjectProxy) facade.retrieveProxy(ProjectProxy.NAME);
        Project project = projectProxy.getProject();

        SceneMediator scene = (SceneMediator) facade.retrieveMediator(SceneMediator.NAME);

        Json json = new Json();
        json.setTypeName(null);
        json.setOutputType(JsonWriter.OutputType.javascript);
        Group container = scene.getViewComponent().getOriginals();
        Skin skin = project.getSkin();
        UIComponent component = new UIComponent();
        ActorData[] actors = component.assemble(skin, container.getChildren());
        json.toJson(actors, new FileHandle(fileName));
    }
}
