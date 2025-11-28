package com.libgdxdesign.menu.mediator;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.libgdxdesign.core.controller.notification.DesignNotification;
import com.libgdxdesign.core.controller.notification.ResourceNotification;
import com.libgdxdesign.menu.view.TopMenu;
import com.libgdxdesign.ui.mediator.UiStageMediator;
import com.libgdxdesign.ui.view.UiStage;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;


public class MenuMediator extends Mediator {
    public static final String NAME = MenuMediator.class.getSimpleName();

    public MenuMediator() {
        super(NAME, new TopMenu());
    }

    @Override
    public TopMenu getViewComponent() {
        return (TopMenu) super.getViewComponent();
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{DesignNotification.CREATE};
    }

    @Override
    public void handleNotification(INotification notification) {
        UiStageMediator sceneMediator = (UiStageMediator) facade.retrieveMediator(UiStageMediator.NAME);
        UiStage stage = sceneMediator.getViewComponent();

        stage.getFullScreenTable().add(getViewComponent()).colspan(2).growX().top().row();
        stage.addPanels();

        FileChooser.setDefaultPrefsName("com.libgdxdesign.editor.filechooser");
        getViewComponent().getSaveMenu().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FileChooser fileChooser = new FileChooser(FileChooser.Mode.SAVE);
                fileChooser.setSelectionMode(FileChooser.SelectionMode.DIRECTORIES);
                fileChooser.setListener(new FileChooserAdapter() {
                    @Override
                    public void selected(Array<FileHandle> files) {
                        facade.sendNotification(ResourceNotification.SAVE_UI_COMPONENT, files.first().file().getAbsolutePath());
                    }
                });
                stage.addActor(fileChooser.fadeIn());
            }
        });

	    getViewComponent().getAbout().addListener(new ClickListener(){
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
			    stage.addActor(createDesignWindow());
		    }
	    });
    }

	public static VisWindow createDesignWindow() {
		VisWindow window = new VisWindow("LibGDX Design");
		window.getTitleLabel().setColor(Color.CYAN);
		window.getTitleTable().padTop(6).padBottom(4);

		VisTable content = new VisTable();
		content.defaults().pad(4);

		VisLabel titleLabel = new VisLabel("LibGDX Design");
		titleLabel.setColor(Color.valueOf("c5c5c5"));

		VisLabel authorLabel = new VisLabel("Author: Andronov Aleksey", "small");
		authorLabel.setColor(Color.valueOf("a0a0a0"));

		content.add(titleLabel).left().row();
		content.add(authorLabel).left().padTop(2).row();

		window.add(content).pad(12).fill().expand();
		window.pack();
		window.setSize(400, 120);
		window.centerWindow();
		return window;
	}
}