package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BatchComponent implements Component {
	public final SpriteBatch batch;

	public BatchComponent(SpriteBatch batch) {
		this.batch = batch;
	}
}
