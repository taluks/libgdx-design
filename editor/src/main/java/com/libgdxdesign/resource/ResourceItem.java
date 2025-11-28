package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.kotcrab.vis.ui.widget.VisLabel;

public class ResourceItem extends Table {

    public interface ResourceItemListener {
        void onItemDropped(CloneableResource<? extends Actor> resource, Actor instance);
    }

    private final DragAndDrop dragAndDrop;
    private final CloneableResource<? extends Actor> resource;
    private final String styleName;
    private ResourceItemListener listener;

    public ResourceItem(CloneableResource<? extends Actor> resource, String name) {
        this.resource = resource;
        this.styleName = name;
        dragAndDrop = new DragAndDrop();

        Label nameLabel = new VisLabel(name);
        nameLabel.setWidth(100);
        nameLabel.setEllipsis(true);
        //nameLabel.setTouchable(Touchable.disabled);
        add(nameLabel).expandX().left();
    }

    public void setDropTarget(Group group) {
        dragAndDrop.setDragActorPosition(-this.getWidth() / 2f, this.getHeight() / 2f);
        dragAndDrop.addSource(new DragAndDrop.Source(this) {
            @Override
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                Actor dragActor = resource.clone();
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setDragActor(dragActor);
                payload.setInvalidDragActor(null);
                return payload;
            }
        });

        dragAndDrop.addTarget(new DragAndDrop.Target(group) {
            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                Actor actor = payload.getDragActor();
                if (listener != null) {
                    listener.onItemDropped(resource, actor);
                }
            }

            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                return true;
            }
        });
    }

    public String getStyleName() {
        return styleName;
    }

    public void setListener(ResourceItemListener listener) {
        this.listener = listener;
    }

    public CloneableResource<? extends Actor> getResource() {
        return resource;
    }
}
