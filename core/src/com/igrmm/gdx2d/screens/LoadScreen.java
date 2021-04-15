package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.igrmm.gdx2d.Gdx2D;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoadScreen extends ScreenAdapter {
	private final Gdx2D game;

	public LoadScreen(Gdx2D game) {
		this.game = game;
	}

	@Override
	public void show() {
		try {
			game.assets.load();
			game.setGameScreen();
		} catch (Exception ex) {
			ex.printStackTrace();
			StringWriter error = new StringWriter();
			ex.printStackTrace(new PrintWriter(error));
			game.setScreen((new ErrorScreen(error.toString())));
		}
	}
}
