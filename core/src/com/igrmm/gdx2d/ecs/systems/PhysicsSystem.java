package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.BroadPhaseCollisionComponent;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem implements System {
	private final ArrayList<BoundingBoxComponent> queue = new ArrayList<>();
	private final Rectangle intersection = new Rectangle();

	@Override
	public void update(EntityManager entityManager) {

		BroadPhaseCollisionComponent broadPhaseCollisionComponent = components.broadPhaseCollisionComponents.get(components.playerID);
		List<Collision> collisions = broadPhaseCollisionComponent.collisions;

		for (Collision collision : collisions) {
			collision.resolve();
		}
		collisions.clear();

		BoundingBoxComponent player = components.dynamicBoundingBoxComponents.get(components.playerID);
		VelocityComponent velocityComponent = components.velocityComponents.get(components.playerID);
		player.x += velocityComponent.velocity.x;
		player.y += velocityComponent.velocity.y;
		velocityComponent.velocity.set(0.0f, 0.0f);
	}
}
