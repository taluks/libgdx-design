package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;

public class CloneableSelectBox implements CloneableResource<SelectBox<?>> {

	private SelectBox<String> selectBox;
	public CloneableSelectBox(SelectBox<String> selectBox) {
		this.selectBox = selectBox;
	}

	@Override
	public SelectBox<?> getActor() {
		return selectBox;
	}

	@Override
	public SelectBox<?> clone() {
		SelectBox<String> clone = new SelectBox<>(selectBox.getStyle());
		clone.setItems(selectBox.getItems());
		return clone;
	}

}
