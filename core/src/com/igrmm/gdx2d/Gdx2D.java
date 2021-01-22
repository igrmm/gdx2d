package com.igrmm.gdx2d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.igrmm.gdx2d.screens.LoadScreen;

public class Gdx2D extends Game {
	public Assets assets;
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public OrthogonalTiledMapRenderer mapRenderer;

	@Override
	public void create() {
		assets = new Assets();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		mapRenderer = new OrthogonalTiledMapRenderer(null);
		setScreen(new LoadScreen(this));
	}

	@Override
	public void dispose() {
		assets.dispose();
		batch.dispose();
		shapeRenderer.dispose();
		mapRenderer.dispose();
	}
}
