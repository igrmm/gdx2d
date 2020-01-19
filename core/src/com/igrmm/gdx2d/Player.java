package com.igrmm.gdx2d;

import com.badlogic.gdx.math.Rectangle;

public class Player extends DynamicGameObject {
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

	public boolean isNear(GameObject gameObject) {
		nearZone.setCenter(getCenter());
		return nearZone.overlaps(gameObject.bounds);
	}

	public String getCurrentMap() {
		return currentMap;
	}

	public String getCurrentWaypoint() {
		return currentWaypoint;
	}
}
