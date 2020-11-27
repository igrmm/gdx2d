package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
	public static final Vector2 VELOCITY_CAP = new Vector2(20.0f, 20.0f);
	public final Vector2 maxVelocity = new Vector2();
	public final Vector2 velocity = new Vector2();
}
