package com.igrmm.gdx2d;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class DynamicGameObject extends GameObject {
	public final Vector2 virtualPosition;
	public final Vector2 velocity;
	public float alpha = 1f;

	public DynamicGameObject(Rectangle bounds) {
		super(bounds);
		virtualPosition = new Vector2(position);
		velocity = new Vector2();
	}

	public void setPosition(float x, float y) {
		position.set(x, y);
		virtualPosition.set(x, y);
		bounds.setPosition(x, y);
	}

	public void update(float delta, ArrayList<GameObject> queue) {
		applyGravity();
		position.lerp(virtualPosition, alpha);
	}

	public void moveUp() {
		virtualPosition.y += velocity.y;
	}

	public void moveDown() {
		virtualPosition.y -= velocity.y;
	}

	public void moveLeft() {
		virtualPosition.x -= velocity.x;
	}

	public void moveRight() {
		virtualPosition.x += velocity.x;
	}

	private void applyGravity() {
		velocity.y = World.G_FORCE;
		moveDown();
	}
}
