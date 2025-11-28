package com.libgdxdesign.ui.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTree;
import com.libgdxdesign.ui.view.library.ItemNode;

import java.util.Collections;
import java.util.List;

public class TreePanel extends Table {

    private Tree tree;
    private Node root;
    private List<Actor> selected = Collections.emptyList();

    public TreePanel() {
      setBackground(VisUI.getSkin().getDrawable("grey"));

        Table table = new Table();

        table.defaults().minSize(150, 220).expand().fill();
        tree = new VisTree();
        root = new ItemNode(new VisLabel("Scene", "menuitem-shortcut"));
        root.setSelectable(false);
        tree.add(root);
        tree.expandAll();

        table.add(tree).expand().fill();
        ScrollPane scrollPane = new ScrollPane(table);
        add(scrollPane).expand().fill();

        // Select current actor on scene
        tree.getSelection().setMultiple(false);
    }

    public void addActorTree(Actor actor) {
        Table table = new Table();
        Label name = new VisLabel(actor.getName());
        name.setName("nameLabel");
        table.add(name).expand().padTop(2);
        Node node = new ItemNode(table);
        node.setValue(actor);

        // У контейнера может быть только один ребенок
        if (actor.hasParent() && actor.getParent() instanceof Container<?>) {
            Node<?, ?, ?> containerNode = root.findNode(actor.getParent());
            if (!containerNode.getChildren().isEmpty()) containerNode.getChildren().get(0).remove();
        }

        Node<Node,?,?> parentNode = actor.hasParent() ? root.findNode(actor.getParent()): null;
        if (parentNode != null) {
            parentNode.add(node);
        } else {
            root.add(node);
        }
        tree.getSelection().set(node);
    }

    public void actorUpdate(Actor actor) {
        Node<?, ?, ?> actorNode = root.findNode(actor);
        if (actorNode != null) {
            Label actorName = ((Table) actorNode.getActor()).findActor("nameLabel");
            actorName.setText(actor.getName());
        }
    }

    public void selectActor(List<Actor> actors) {
        tree.getSelection().clear();
        Array<Node<?, ?, ?>> nodes = new Array<>();
        for (Actor actor : actors) {
            if (actor == null) continue;
            Node<?, ?, ?> node = root.findNode(actor);
            if (node != null) nodes.add(node);
        }
        selected = actors;
        tree.getSelection().addAll(nodes);
    }

    public void removeActorsTree(List<Actor> actors) {
        tree.getSelection().clear();
        for (Actor actor : actors) {
            Node<?, ?, ?> node = root.findNode(actor);
            if (node != null)
                node.remove();
        }
    }

    public void removeChildrenTree(Actor actor) {
        tree.getSelection().clear();
        Node<?, ?, ?> node = root.findNode(actor);
        if (node != null) {
            for (Node<?, ?, ?> child : node.getChildren()) {
                removeChildrenTree(child.getActor());
                child.remove();
            }
        }
    }

    public void deselect() {
        tree.getSelection().clear();
    }

    public Tree getTree() {
        return tree;
    }

    public List<Actor> getSelected() {
        return selected;
    }
}
