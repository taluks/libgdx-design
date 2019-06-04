package com.libgdxdesign.view.lilbrary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.libgdxdesign.core.resource.ResourceItem;

public class LilbraryPanel extends Table {

	private SelectBox<String> selectBox;
	private Table table;
	private Group dropTarget;
	private Map<String, List<ResourceItem>> resourceMap = new HashMap<>();
	private String currentClass;

	public LilbraryPanel(Skin skin, Group dropTarget) {
		super(skin);
		this.dropTarget = dropTarget;

		table = new Table(skin);

		selectBox = new SelectBox<>(skin);
		selectBox.setItems("All");
		selectBox.setSelectedIndex(0);
		currentClass = selectBox.getSelected();
		add(selectBox).top().right().padRight(20).expand().row();

		ScrollPane scrollPane = new ScrollPane(table);
		add(scrollPane).expand().fill();

		setBackground(skin.getDrawable("list"));

		selectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				currentClass = selectBox.getSelected();
				displayResources(currentClass);
			}
		});
	}

	public void addItems(List<ResourceItem> list) {
		if (list.size() == 0)
			return;
		String className = list.get(0).getResource().getActor().getClass().getSimpleName();
		Array<String> selectBoxItems = selectBox.getItems();
		selectBoxItems.add(className);
		selectBox.setItems(selectBoxItems);
		resourceMap.put(className, list);
	}
	
	public void clearItems() {
		selectBox.setItems("All");
		table.clear();
	}

	public void displayResources() {
		displayResources(currentClass);
	}

	public void displayResources(String className) {
		table.clear();
		if (className.equals(selectBox.getItems().get(0))) {
			for (List<ResourceItem> list : resourceMap.values()) {
				displayList(list);
			}

		} else {
			displayList(resourceMap.get(className));
		}
	}

	private void displayList(List<ResourceItem> list) {
		for (ResourceItem item : list) {
			displayItem(item);
		}
	}

	private void displayItem(ResourceItem item) {
		table.add(item).maxWidth(100).padRight(4);
		item.setDropTarget(dropTarget);
	}

}
