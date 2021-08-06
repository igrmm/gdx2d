package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BoundingBoxComponent implements Component {
	public final Rectangle bBox;
	public final Vector2 previousPosition;

	public BoundingBoxComponent() {
		bBox = new Rectangle();
		previousPosition = new Vector2();
	}

	public BoundingBoxComponent(float x, float y, float width, float height) {
		bBox = new Rectangle(x, y, width, height);
		previousPosition = new Vector2(x, y);
	}

	public BoundingBoxComponent(Rectangle bBox) {
		this.bBox = bBox;
		previousPosition = new Vector2(bBox.x, bBox.y);
	}
}
