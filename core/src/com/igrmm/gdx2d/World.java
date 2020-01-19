package com.igrmm.gdx2d;

import com.igrmm.gdx2d.entities.Entities;
import com.igrmm.gdx2d.entities.Entity;

import java.util.ArrayList;

public class World {
	public static final float G_FORCE = 10f;
	public final Player player;
	private final Entities entities;
	private final ArrayList<GameObject> queue;

	public World(Player player, Entities entities) {
		this.player = player;
		this.entities = entities;
		queue = new ArrayList<>();
		queue.add(player);

		/* Set player initial position */
		Entity waypoint = entities.get(player.getCurrentWaypoint());
		player.setPosition(waypoint.getX(), waypoint.getY());
	}

	public void update(float delta) {

		for (Entity e : entities) {
			if (player.isNear(e) && !queue.contains(e))
				queue.add(e);
		}

		for (GameObject gameObject : queue) {
			gameObject.update(delta, queue);
		}

		handleCollision(player);
	}

	public void handleCollision(DynamicGameObject gameObject) {

		float stepYMax, stepXMax;

		do {
			stepYMax = gameObject.position.y - gameObject.bounds.y;

			if (stepYMax > 0) {
				gameObject.bounds.y += (stepYMax >= 1) ? 1 : stepYMax;
				for (Entity e : entities) {
					if (e.bounds.overlaps(gameObject.bounds)) {
						if (e.isCollidable()) {
							gameObject.bounds.y = e.bounds.y - gameObject.getHeight();
							gameObject.position.y = gameObject.bounds.y;
							gameObject.virtualPosition.y = gameObject.bounds.y;
						}
						e.handle(gameObject);
					}
				}
			}

			if (stepYMax < 0) {
				gameObject.bounds.y += (stepYMax <= -1) ? -1 : stepYMax;
				for (Entity e : entities) {
					if (e.bounds.overlaps(gameObject.bounds)) {
						if (e.isCollidable()) {
							gameObject.bounds.y = e.bounds.y + e.getHeight();
							gameObject.position.y = gameObject.bounds.y;
							gameObject.virtualPosition.y = gameObject.bounds.y;
						}
						e.handle(gameObject);
					}
				}
			}

			stepXMax = gameObject.position.x - gameObject.bounds.x;

			if (stepXMax > 0) {
				gameObject.bounds.x += (stepXMax >= 1) ? 1 : stepXMax;
				for (Entity e : entities) {
					if (e.bounds.overlaps(gameObject.bounds)) {
						if (e.isCollidable()) {
							gameObject.bounds.x = e.bounds.x - gameObject.getWidth();
							gameObject.position.x = gameObject.bounds.x;
							gameObject.virtualPosition.x = gameObject.bounds.x;
						}
						e.handle(gameObject);
					}
				}
			}

			if (stepXMax < 0) {
				gameObject.bounds.x += (stepXMax <= -1) ? -1 : stepXMax;
				for (Entity e : entities) {
					if (e.bounds.overlaps(gameObject.bounds)) {
						if (e.isCollidable()) {
							gameObject.bounds.x = e.bounds.x + e.getWidth();
							gameObject.position.x = gameObject.bounds.x;
							gameObject.virtualPosition.x = gameObject.bounds.x;
						}
						e.handle(gameObject);
					}
				}
			}
		} while (stepXMax != 0 || stepYMax != 0);
	}
}
