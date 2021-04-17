package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class BatchComponent implements Component, Disposable {
	public boolean dispose = false;
	public final SpriteBatch batch = new SpriteBatch();

	@Override
	public void dispose() {
		dispose = true;
		batch.dispose();
	}
}
