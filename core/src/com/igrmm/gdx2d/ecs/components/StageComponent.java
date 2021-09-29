package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StageComponent implements Component, Disposable, Resizable {
	public final Stage stage = new Stage(new ScreenViewport());

	//temp until setup asset manager
	public final Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
}
