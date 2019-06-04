package com.libgdxdesign.core.event;

public class ResizeEvent {

	private final int width;
	private final int height;

	public ResizeEvent(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
