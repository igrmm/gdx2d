package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.igrmm.gdx2d.Gdx2D;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoadScreen extends AbstractScreen {
	public LoadScreen(Gdx2D game) {
		super(game);
	}

	@Override
	public void show() {
		try {
			game.assets.load("maps/start.tmx", TiledMap.class);
			game.assets.load("images/player.png", Texture.class);
			game.assets.finishLoading();
			game.setScreen(new GameScreen(game));
		} catch (Exception ex) {
			ex.printStackTrace();
			StringWriter error = new StringWriter();
			ex.printStackTrace(new PrintWriter(error));
			game.setScreen((new ErrorScreen(error.toString())));
		}
	}
}
