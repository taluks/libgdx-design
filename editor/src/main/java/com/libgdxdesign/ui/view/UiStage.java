package com.libgdxdesign.ui.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;

public class UiStage extends Stage {
	private final Group dummyTarget;
	private final Table fullScreenTable;
	private final Table leftTable;
	private final Table rightTable;
	private final TabbedPane rightTabbedPane;
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

		leftTable = new Table();
		rightTable = new Table();
		rightTabbedPane = new TabbedPane();

		transformRectangle = new TransformRectangle();
		transformRectangle.setTouchable(Touchable.childrenOnly);
		addActor(transformRectangle);
	}

	public void addPanels() {
		fullScreenTable.add(leftTable).expand().grow();
		fullScreenTable.add(rightTable).right().top().growY();
	}

	public TransformRectangle getSelectionRectangle() {
		return transformRectangle;
	}

	public Table getFullScreenTable() {
		return fullScreenTable;
	}

	public Table getLeftTable() {
		return leftTable;
	}

	public Table getRightTable() {
		return rightTable;
	}

	public TabbedPane getRightTabbedPane() {
		return rightTabbedPane;
	}

	public Group getDummyTarget() {
		return dummyTarget;
	}
}
