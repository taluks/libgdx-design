package com.libgdxdesign.component;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.SerializationException;
import com.libgdxdesign.component.loader.ActorDataSerializer;
import com.libgdxdesign.component.loader.UIComponentSerializer;
import com.libgdxdesign.component.loader.assembler.ActorAssembler;
import com.libgdxdesign.component.loader.assembler.ButtonAssembler;
import com.libgdxdesign.component.loader.assembler.CheckBoxAssembler;
import com.libgdxdesign.component.loader.assembler.DialogAssembler;
import com.libgdxdesign.component.loader.assembler.GroupAssembler;
import com.libgdxdesign.component.loader.assembler.ImageAssembler;
import com.libgdxdesign.component.loader.assembler.ImageButtonAssembler;
import com.libgdxdesign.component.loader.assembler.ImageTextButtonAssembler;
import com.libgdxdesign.component.loader.assembler.LabelAssembler;
import com.libgdxdesign.component.loader.assembler.ProgressBarAssembler;
import com.libgdxdesign.component.loader.assembler.ProxyAssembler;
import com.libgdxdesign.component.loader.assembler.SelectBoxAssembler;
import com.libgdxdesign.component.loader.assembler.TableAssembler;
import com.libgdxdesign.component.loader.assembler.TextAreaAssembler;
import com.libgdxdesign.component.loader.assembler.TextButtonAssembler;
import com.libgdxdesign.component.loader.assembler.TextFieldAssembler;
import com.libgdxdesign.component.loader.assembler.WindowAssembler;
import com.libgdxdesign.component.model.ActorData;

public class UIComponent implements ProxyAssembler{
	
	private final Json json = new Json();
	private final ObjectSet<ActorData> actorsDataSet = new ObjectSet<>();
	private final ObjectMap<Class<?>, ActorAssembler<?, ?>> assemblers = new ObjectMap<>();
	
	public UIComponent() {
		register(new LabelAssembler());
		register(new ImageAssembler());
		register(new ButtonAssembler());
		register(new TextButtonAssembler());
		register(new ImageButtonAssembler());
		register(new ImageTextButtonAssembler());
		register(new CheckBoxAssembler());
		register(new TextFieldAssembler());
		register(new TextAreaAssembler());
		register(new ProgressBarAssembler());
		register(new DialogAssembler());		
		register(new GroupAssembler());		
		register(new SelectBoxAssembler());			
		register(new WindowAssembler());
		register(new TableAssembler());
		
		
		json.setIgnoreUnknownFields(true);
		json.setUsePrototypes(false);
		json.setSerializer(UIComponent.class, new UIComponentSerializer(this));
		json.setSerializer(ActorData.class, new ActorDataSerializer());
		
	}
	
	public UIComponent register(ActorAssembler<?, ?> actorAssembler) {
		json.addClassTag(actorAssembler.getActorClass().getName(), actorAssembler.getActorDataClass());
		assemblers.put(actorAssembler.getActorDataClass(), actorAssembler);
		return this;
	}
	
	public ObjectMap<String, Actor> build(Skin skin) throws GdxRuntimeException {
		ObjectMap<String, Actor> result = new ObjectMap<>();
		for(ActorData actorData: actorsDataSet) {
			Actor actor = this.getBuilder(actorData.getClass()).build(this, skin, actorData);
			result.put(actorData.name, actor);
		}		
		return result;
	}
	
	public ActorData assemble(Skin skin, Actor actor){
		ActorData actorData = getAssembler(actor.getClass()).assemble(this, skin, actor);
		actorsDataSet.add(actorData);
		return actorData;
	}

	public ActorData[] assemble(Skin skin, Array<Actor> actors) {
		Actor[] temp = actors.shrink();
		ActorData[] result = new ActorData[temp.length];
		for (int i = 0; i < temp.length; i++) {
			result[i] = assemble(skin, temp[i]);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActorAssembler<Actor, ActorData> getAssembler(Class<? extends Actor> type) {
		Class<? extends ActorData> typeData = json.getClass(type.getName());
		if(typeData == null) return null;
		return (ActorAssembler<Actor, ActorData>) assemblers.get(typeData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActorAssembler<Actor, ActorData> getBuilder(Class<? extends ActorData> type) {
		return (ActorAssembler<Actor, ActorData>) assemblers.get(type);
	}
	
	public UIComponent load(FileHandle file) {
		actorsDataSet.clear();
		try {					
			return json.fromJson(UIComponent.class, file);
		} catch (SerializationException ex) {
			throw new SerializationException("Error reading file: " + file, ex);
		}		
	}
	
	public void clear() {
		actorsDataSet.clear();		
	}
	
	public void add(ActorData actorData) {
		actorsDataSet.add(actorData);
	}

	public ObjectSet<ActorData> getActorsDataSet() {
		return actorsDataSet;
	}	
	
}
