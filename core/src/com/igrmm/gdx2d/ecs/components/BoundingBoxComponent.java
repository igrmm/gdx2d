package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.math.Rectangle;

public class BoundingBoxComponent extends Rectangle implements Component {
	public BoundingBoxComponent(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public BoundingBoxComponent(Rectangle rect) {
		super(rect);
	}

	public BoundingBoxComponent() {
	}
}
