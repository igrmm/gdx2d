package com.igrmm.gdx2d;

import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.entities.Entity;

import java.util.ArrayList;

public class Player extends DynamicGameObject implements Entity {
	/* Serializable */
	private String currentMap = "maps/start.tmx";
	private String currentWaypoint = "wp_origin";

	private static final float WIDTH = 32;
	private static final float HEIGHT = 32;

	private transient final Rectangle farZone, nearZone, closeZone;

	/* Serializable classes (json) needs no-argument constructor */
	public Player() {
		super(new Rectangle(0, 0, WIDTH, HEIGHT));
		velocity.set(5f, 5f);
		alpha = 0.2f;

		farZone = new Rectangle();
		nearZone = new Rectangle();
		closeZone = new Rectangle();
	}

	public void setZones(float screenWidth, float screenHeight) {
		farZone.setSize(screenWidth * 3f, screenHeight * 3f);
		nearZone.setSize(screenWidth * 1.5f, screenHeight * 1.5f);
		closeZone.setSize(screenWidth, screenHeight);
	}

	public boolean isNear(Rectangle bounds) {
		nearZone.setCenter(getCenter());
		return nearZone.overlaps(bounds);
	}

	public String getCurrentMap() {
		return currentMap;
	}

	public String getCurrentWaypoint() {
		return currentWaypoint;
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void update(float delta, ArrayList<Entity> queue) {
		applyGravity();
		position.lerp(virtualPosition, alpha);
	}

	@Override
	public void handle(DynamicGameObject dynamicGameObject) {
	}
}
