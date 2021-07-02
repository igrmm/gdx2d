package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.SerializationException;
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
			game.saves.load();
		} catch (SerializationException ex) {
			Gdx.app.error("Serialization Exception", "Save file is corrupt");
		}

		try {
			game.assets.load();
			game.setNewGameScreen();
		} catch (Exception ex) {
			ex.printStackTrace();
			StringWriter error = new StringWriter();
			ex.printStackTrace(new PrintWriter(error));
			game.setScreen((new ErrorScreen(error.toString())));
		}
	}
}
