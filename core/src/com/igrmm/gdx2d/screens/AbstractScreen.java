package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.igrmm.gdx2d.Gdx2D;

public abstract class AbstractScreen extends ScreenAdapter {
	protected final Gdx2D game;
	protected final SpriteBatch batch;

	public AbstractScreen(Gdx2D game) {
		this.game = game;
		batch = new SpriteBatch();
	}

	public void update(float delta) {
	}

	public void draw() {
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
