package com.igrmm.gdx2d;

import com.badlogic.gdx.Game;
import com.igrmm.gdx2d.screens.LoadScreen;

public class Gdx2D extends Game {
	public final Assets assets = new Assets();

	@Override
	public void create() {
		setScreen(new LoadScreen(this));
	}

	@Override
	public void dispose() {
		assets.dispose();
	}
}
