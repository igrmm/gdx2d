package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {
	public static final int RIGHT_DIRECTION = 1;
	public static final int LEFT_DIRECTION = -1;

	public float maxSpeed = 3.0f;
	public float acceleration = 0.3f;
	public float friction = 0.2f;
	public float gravity = -0.5f;
	public float jumpForce = 10.0f;
	public boolean grounded = false;
	public boolean jumping = false;
	public Vector2 speed = new Vector2();

	public boolean jumpInput = false;
	public int directionInput = 0;
}
