package com.libgdxdesign.component.model;

public abstract class ActorData {
	public String name;
	public String className;
	public float x, y;
	public float width, height;
	public float originX, originY;
	public float scaleX = 1, scaleY = 1;
	public float rotation;
	public boolean visible;
	public int z;

	public ActorData() {
		
	}
}
