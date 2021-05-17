package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.math.Rectangle;

public class BoundingBoxComponent implements Component {
	public final Rectangle bBox;

	public BoundingBoxComponent() {
		bBox = new Rectangle();
	}

	public BoundingBoxComponent(float x, float y, float width, float height) {
		bBox = new Rectangle(x, y, width, height);
	}

	public BoundingBoxComponent(Rectangle bBox) {
		this.bBox = bBox;
	}
}
