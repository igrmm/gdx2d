package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class BatchComponent implements Component, Disposable {
	public final SpriteBatch batch = new SpriteBatch();

	@Override
	public void dispose() {
		batch.dispose();
	}
}
