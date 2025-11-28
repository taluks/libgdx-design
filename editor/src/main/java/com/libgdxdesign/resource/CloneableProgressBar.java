package com.libgdxdesign.resource;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class CloneableProgressBar implements CloneableResource<ProgressBar> {

	private ProgressBar progressBar;

	public CloneableProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	@Override
	public ProgressBar getActor() {
		return progressBar;
	}

	@Override
	public ProgressBar clone() {
		return new ProgressBar(progressBar.getMinValue(), progressBar.getMaxValue(), progressBar.getStepSize(),
				progressBar.isVertical(), progressBar.getStyle());
	}

}
