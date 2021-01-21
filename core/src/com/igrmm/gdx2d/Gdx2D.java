package com.igrmm.gdx2d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.igrmm.gdx2d.screens.LoadScreen;

public class Gdx2D extends Game {
	public final Assets assets = new Assets();
	public SpriteBatch batch;
	public OrthogonalTiledMapRenderer mapRenderer;

	@Override
	public void create() {
		batch = new SpriteBatch();
		mapRenderer = new OrthogonalTiledMapRenderer(null);
		setScreen(new LoadScreen(this));
	}

	@Override
	public void dispose() {
		assets.dispose();
		batch.dispose();
		mapRenderer.dispose();
	}
}
