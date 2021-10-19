package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StageComponent implements Component, Disposable, Resizable {
	public final Stage stage;
	public final Skin skin;

	public StageComponent(Skin skin) {
		stage = new Stage(new ScreenViewport());
		this.skin = skin;
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
}
