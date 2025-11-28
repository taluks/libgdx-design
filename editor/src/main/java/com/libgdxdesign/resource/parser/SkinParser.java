package com.libgdxdesign.resource.parser;

import java.util.LinkedHashSet;
import java.util.Set;

public class SkinParser {

	private final Set<ResourceParser> list = new LinkedHashSet<>();

	public SkinParser() {
		register(new ButtonParser());
		register(new CheckBoxParser());
		register(new ImageButtonParser());
		register(new SelectBoxParser());
		register(new TouchpadParser());
		register(new ProgressBarParser());
		register(new ImageTextButtonParser());
		register(new TextButtonParser());
		register(new WindowParser());
		register(new TextureRegionParser());
		register(new ListParser());
		register(new LabelParser());
	}
	
	public void register(ResourceParser parser) {
		list.add(parser);
	}
	
	public Set<ResourceParser> getParsers(){
		return list;
	}
}