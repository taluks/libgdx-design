package com.libgdxdesign.component;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.*;
import com.libgdxdesign.component.loader.ActorDataSerializer;
import com.libgdxdesign.component.loader.CellDataSerializer;
import com.libgdxdesign.component.loader.UIComponentSerializer;
import com.libgdxdesign.component.loader.assembler.*;
import com.libgdxdesign.component.model.ActorData;
import com.libgdxdesign.component.model.CellData;

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
		json.setSerializer(CellData.class, new CellDataSerializer());
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
