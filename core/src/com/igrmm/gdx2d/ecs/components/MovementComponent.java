package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {
	public static final float RIGHT_DIRECTION = 1.0f;
	public static final float LEFT_DIRECTION = -1.0f;

	public float maxSpeed = 4.0f;
	public float acceleration;
	public float friction;
	public float gravity = -0.5f;
	public float jumpForce = 10.0f;

	public float direction = 0.0f;
	public Vector2 speed = new Vector2();
	public boolean grounded = false;
	public boolean jumped = false;
}
