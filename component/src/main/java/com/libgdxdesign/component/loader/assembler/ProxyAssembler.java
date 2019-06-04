package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.libgdxdesign.component.model.ActorData;

public interface ProxyAssembler {

	ActorAssembler<Actor, ActorData> getAssembler(Class<? extends Actor> type);

	ActorAssembler<Actor, ActorData> getBuilder(Class<? extends ActorData> type);
	
}
