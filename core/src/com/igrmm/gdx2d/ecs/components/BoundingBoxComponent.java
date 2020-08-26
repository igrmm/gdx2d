package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.math.Rectangle;

public class BoundingBoxComponent extends Rectangle implements Component {
	public float oldX, oldY;

	public BoundingBoxComponent(float x, float y, float width, float height) {
		super(x, y, width, height);
		oldX = x;
		oldY = y;
	}

	public BoundingBoxComponent(Rectangle rect) {
		super(rect);
		oldX = rect.x;
		oldY = rect.y;
	}

	public BoundingBoxComponent() {
	}
}
