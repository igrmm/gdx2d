package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.math.Vector2;

public class AccelerationComponent implements Component {
	public final Vector2 acceleration;

	public AccelerationComponent() {
		acceleration = new Vector2();
	}

	public AccelerationComponent(float x, float y) {
		acceleration = new Vector2(x, y);
	}
}
