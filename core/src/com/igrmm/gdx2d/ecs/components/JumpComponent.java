package com.igrmm.gdx2d.ecs.components;

public class JumpComponent implements Component {
	public boolean grounded = false;
	public boolean jumped = false;
	public float jumpVelocity = 10.0f;
}
