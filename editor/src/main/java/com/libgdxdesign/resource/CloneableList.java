package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.List;

public class CloneableList implements CloneableResource<List<?>> {

	private List<?> list;

	public CloneableList(List<?> list) {
		this.list = list;
	}

	@Override
	public List<?> getActor() {
		return list;
	}

	@Override
	public List<?> clone() {
		return new List<>(list.getStyle());
	}

}
