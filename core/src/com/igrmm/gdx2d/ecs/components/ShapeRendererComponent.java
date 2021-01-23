package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class ShapeRendererComponent implements Component, Disposable {
	public final ShapeRenderer shapeRenderer = new ShapeRenderer();

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
}
