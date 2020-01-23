package com.igrmm.gdx2d;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class GameObject {
	public final Rectangle bounds;
	public final Vector2 position;
	private final Vector2 center;

	public GameObject(Rectangle bounds) {
		this.bounds = bounds;
		position = new Vector2(bounds.x, bounds.y);
		center = new Vector2();
		bounds.getCenter(center);
	}

	public Vector2 getCenter() {
		return bounds.getCenter(center);
	}

	public float getX() {
		return bounds.getX();
	}

	public float getY() {
		return bounds.getY();
	}

	public float getWidth() {
		return bounds.getWidth();
	}

	public float getHeight() {
		return bounds.getHeight();
	}
}
