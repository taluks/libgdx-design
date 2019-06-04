package com.libgdxdesign.view.tree;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.libgdxdesign.core.event.ActorAddEvent;
import com.libgdxdesign.core.event.ActorRemoveEvent;
import com.libgdxdesign.core.event.ActorSelectEvent;
import com.libgdxdesign.core.event.ActorUpdateEvent;

public class TreePanel extends Table {

	private final EventBus eventBus;
	private Skin skin;
	private Tree tree;
	private Node root;
	private Actor lastSelectedActor;
	private Group scene;

	public TreePanel(Group scene, EventBus eventBus, Skin skin) {
		// super("Items Tree", skin, "box");
		this.scene = scene;
		this.eventBus = eventBus;
		this.skin = skin;

		setBackground(skin.getDrawable("list"));

		Table table = new Table();

		// table.defaults().minSize(150, 220).expand().fill();
		tree = new Tree(skin);
		root = new Node(new Label("Scene ", skin));
		root.setObject(scene);
		tree.add(root);
		tree.getSelection().set(root);
		tree.expandAll();

		table.add(tree).expand().fill();

		ScrollPane scrollPane = new ScrollPane(table);
		add(scrollPane).expand().fill();

		eventBus.register(this);

		// Select current actor on scene
		tree.getSelection().setMultiple(false);
		tree.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Node lastSelected = tree.getSelection().getLastSelected();
				if (lastSelected != null) {
					Actor selectedActor = (Actor) lastSelected.getObject();
					if (selectedActor != null && lastSelectedActor != selectedActor) {
						eventBus.post(new ActorSelectEvent(selectedActor));
						lastSelectedActor = selectedActor;
					}
				}
			}
		});

		pack();
	}

	@Subscribe
	private void actorUpdateEvent(ActorUpdateEvent event) {
		Actor actor = event.getActor();
		Node actorNode = root.findNode(actor);
		if (actorNode != null) {
			Label actorName = ((Table) actorNode.getActor()).findActor("name");
			actorName.setText(actor.getName());
		}
	}

	@Subscribe
	private void actorRemoveEvent(ActorRemoveEvent event) {
		Actor actor = event.getActor();
		Node lastSelected = tree.getSelection().getLastSelected();
		if (lastSelected != null && actor != null && lastSelected.getObject() == actor
				&& !root.getChildren().isEmpty()) {
			Node node = root.getChildren().peek();
			if (node != null) {
				tree.getSelection().set(node);
				lastSelectedActor = actor;
			}
		}
	}

	@Subscribe
	private void selectActor(ActorSelectEvent event) {
		Actor actor = event.getActor();
		Node lastSelected = tree.getSelection().getLastSelected();
		if (lastSelected != null && actor != null && !(lastSelected.getObject() == actor)) {
			Node node = root.findNode(actor);
			if (node != null) {
				tree.getSelection().set(node);
				lastSelectedActor = actor;
			}
		}

	}

	@Subscribe
	private void addActor(ActorAddEvent event) {
		Actor actor = event.getActor();

		Table table = new Table();
		if (actor instanceof Group) {
			ImageButton lockBtn = new ImageButton(skin, "group-lock");
			lockBtn.setChecked(true);
			lockBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {

				}
			});
			table.add(lockBtn).padLeft(2).padRight(2);
		}
		Label name = new Label(actor.getName(), skin);
		name.setName("name");
		table.add(name).padTop(2);
		Node node = new Node(table);
		node.setObject(actor);

		Node parentNode = root.findNode(actor.getParent());
		if (parentNode != null) {
			parentNode.add(node);
		} else {
			root.add(node);
		}
		lastSelectedActor = actor;
		tree.getSelection().set(node);
	}

	@Subscribe
	private void removeActor(ActorRemoveEvent event) {
		Actor actor = event.getActor();
		Node node = root.findNode(actor);
		if (node != null) {
			node.remove();
		}
	}

}
