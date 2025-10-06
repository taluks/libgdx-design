package com.libgdxdesign.ui.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.libgdxdesign.scene.view.TransformRectangle;

public class UiStage extends Stage {
	private final Group dummyTarget;
	private final Table fullScreenTable;
	private final TransformRectangle transformRectangle;

	public UiStage() {
		super(new ScreenViewport());
		dummyTarget = new Group();
		dummyTarget.setPosition(0, 0);
		addActor(dummyTarget);

		fullScreenTable = new Table();
		fullScreenTable.setFillParent(true);
		fullScreenTable.top().left();
		addActor(fullScreenTable);

		transformRectangle = new TransformRectangle();
	}

	public void selectActor(Actor actor) {
		if (!actor.hasParent())
			return;
		transformRectangle.clear();
		transformRectangle.setVisible(true);
		transformRectangle.setActor(actor);
		//routePanel.setActor(actor);
	}

	public void unselectActor(Actor actor) {
		transformRectangle.clear();
		transformRectangle.setVisible(false);
	}

	public TransformRectangle getSelectionRectangle() {
		return transformRectangle;
	}

	public Table getFullScreenTable() {
		return fullScreenTable;
	}

	public Group getDummyTarget() {
		return dummyTarget;
	}
}
