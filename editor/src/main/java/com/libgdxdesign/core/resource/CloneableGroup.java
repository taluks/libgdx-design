package com.libgdxdesign.core.resource;

import com.badlogic.gdx.scenes.scene2d.Group;

public class CloneableGroup implements CloneableResource<Group> {
	private Group group;

	public CloneableGroup(Group group) {
		this.group = group;
	}

	@Override
	public Group getActor() {
		return group;
	}

	@Override
	public Group clone() {
		Group clone = new Group();
		clone.setSize(group.getWidth(), group.getHeight());
		return clone;
	}

}
