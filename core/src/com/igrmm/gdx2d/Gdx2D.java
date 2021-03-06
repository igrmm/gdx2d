package com.igrmm.gdx2d;

import com.badlogic.gdx.Game;
import com.igrmm.gdx2d.screens.GameScreen;
import com.igrmm.gdx2d.screens.LoadScreen;

public class Gdx2D extends Game {
	public final Assets assets = new Assets();
	public final Saves saves = new Saves();
	private GameScreen gameScreen;

	@Override
	public void create() {
		setScreen(new LoadScreen(this));
	}

	public void setNewGameScreen() {
		if (gameScreen != null) gameScreen.dispose();
		gameScreen = new GameScreen(this);
		setGameScreen();
	}

	public void setGameScreen() {
		if (gameScreen != null) {
			setScreen(gameScreen);
		} else {
			setNewGameScreen();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
		gameScreen.dispose();
	}
}
