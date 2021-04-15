package com.igrmm.gdx2d;

import com.badlogic.gdx.Game;
import com.igrmm.gdx2d.screens.GameScreen;
import com.igrmm.gdx2d.screens.LoadScreen;

public class Gdx2D extends Game {
	public final Assets assets = new Assets();
	private final GameScreen gameScreen = new GameScreen(this);

	@Override
	public void create() {
		setScreen(new LoadScreen(this));
	}

	public void setGameScreen() {
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
		gameScreen.dispose();
	}
}
