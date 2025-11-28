package com.libgdxdesign.core.controller;

import com.libgdxdesign.core.mediator.DesignScreenMediator;
import com.libgdxdesign.core.mediator.ResourceMediator;
import com.libgdxdesign.core.mediator.SimpleMediator;
import com.libgdxdesign.menu.mediator.MenuMediator;
import com.libgdxdesign.scene.mediator.SceneMediator;
import com.libgdxdesign.ui.mediator.*;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class BootstrapViewCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        facade.registerMediator(new SimpleMediator());
        facade.registerMediator(new DesignScreenMediator());
        facade.registerMediator(new SceneMediator());
        facade.registerMediator(new UiStageMediator());

        facade.registerMediator(new ResourceMediator());
        facade.registerMediator(new MenuMediator());
        facade.registerMediator(new PropertiesPanelMediator());
        facade.registerMediator(new LibraryPanelMediator());
        facade.registerMediator(new TreePanelMediator());
        facade.registerMediator(new BreadcrumbsMediator());
        facade.registerMediator(new ContextMenuMediator());
    }
}
