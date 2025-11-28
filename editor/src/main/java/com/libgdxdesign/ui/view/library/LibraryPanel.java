package com.libgdxdesign.ui.view.library;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisSplitPane;
import com.kotcrab.vis.ui.widget.VisTree;
import com.libgdxdesign.resource.ResourceItem;

import java.util.List;
import java.util.Optional;

public class LibraryPanel extends Table {

	private final VisTree tree = new VisTree();
	private final Table container = new Table();

	public LibraryPanel() {
		tree.setIconSpacing(5, 5);
		tree.getSelection().setMultiple(false);

		VisScrollPane treeScrollPane = new VisScrollPane(tree);
		treeScrollPane.setFadeScrollBars(false);
		treeScrollPane.setScrollingDisabled(true, false);

		container.setClip(true);

		Table previewTable = new Table();
		previewTable.add(container).center();

		VisSplitPane splitPane = new VisSplitPane(previewTable, treeScrollPane, true);
		splitPane.setSplitAmount(0.2f);
		splitPane.setMinSplitAmount(0.2f);
		splitPane.setMaxSplitAmount(0.8f);

		add(splitPane).width(250).grow();

		tree.addListener(new ChangeListener() {
			@SuppressWarnings("rawtypes")
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Node<Node, Actor, Actor> lastSelected = (Node<Node, Actor, Actor>) tree.getSelection()
						.getLastSelected();

				container.clear();
				if (lastSelected == null)
					return;
				if (lastSelected.getActor() instanceof ResourceItem) {
					ResourceItem resourceItem = (ResourceItem) lastSelected.getActor();
					container.add(resourceItem.getResource().clone()).center(); // Центрируем превью
				}
			}
		});

		setTouchable(Touchable.enabled);
		//debugAll();
	}

	public void clear() {
		//tree.getC
	}

	public void expandAll() {
		tree.expandAll();
	}

	public void addItems(List<ResourceItem> items, String category) {
		Optional<ResourceItem> firstItem = items.stream().findFirst();
		if (firstItem.isEmpty())
			return;
		Actor actor = new VisLabel(category, "menuitem-shortcut");

		ItemNode typeNode = new ItemNode(actor);
		for (ResourceItem resourceItem : items) {
			typeNode.add(new ItemNode(resourceItem));
		}
		tree.add(typeNode);
		invalidateHierarchy();
	}
}