package com.libgdxdesign.ui.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.PopupMenu;
import com.libgdxdesign.ui.event.ActionEvent;
import com.libgdxdesign.component.loader.TableUtils;


public class ContextMenu extends Table {

    public enum Action {
        ADD_CELL,
        ROW,
        CLEAR_CHILDREN,
        FILL,
        RESET_CELL,
        EXPAND,
    }

    private final PopupMenu menu;

    public ContextMenu() {
        menu = new PopupMenu();
    }

    private MenuItem item(String title, Action action) {
        return new MenuItem(title, new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fire(new ActionEvent<>(action));
            }
        });
    }

    public void rebuildFor(Actor actor) {
        menu.clear();

        menu.addItem(item("Add cell", Action.ADD_CELL));
        menu.addItem(item("Row", Action.ROW));
        menu.addItem(item("Clear children", Action.CLEAR_CHILDREN));

        if (TableUtils.hasCellFor(actor)) {
            menu.addSeparator();
            menu.addItem(item("Fill cell", Action.FILL));
            menu.addItem(item("Reset cell", Action.RESET_CELL));
            menu.addItem(item("Expand", Action.EXPAND));
        }
    }

    public PopupMenu getMenu() {
        return menu;
    }

}
