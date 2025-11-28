package com.libgdxdesign.component.loader.assembler;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.libgdxdesign.component.model.ProgressBarData;

public class ProgressBarAssembler extends ActorAssembler<ProgressBar, ProgressBarData> {

	@Override
	public ProgressBar build(ProxyAssembler proxy, Skin skin, ProgressBarData data) {
		ProgressBar progressBar = new ProgressBar(data.min, data.max, data.stepSize, data.vertical, skin, data.styleName);
		return buildParameters(proxy, skin, progressBar, data);
	}

	@Override
	public ProgressBarData assemble(ProxyAssembler proxy, Skin skin, ProgressBar actor) {
		ProgressBarData data = new ProgressBarData();
		data.min = actor.getMinValue();
		data.max = actor.getMaxValue();
		data.stepSize = actor.getStepSize();
		data.vertical = actor.isVertical();
		data.styleName = skin.find(actor.getStyle());
		return assembleParameters(proxy, skin, data, actor);
	}

}
