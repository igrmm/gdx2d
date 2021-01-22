package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShapeRendererComponent implements Component {
	public final ShapeRenderer shapeRenderer;

	public ShapeRendererComponent(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}
}
