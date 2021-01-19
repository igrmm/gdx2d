package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.BroadPhaseCollisionComponent;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

import java.util.List;

public class PhysicsSystem implements System {

	@Override
	public void update(EntityManager entityManager, float delta) {

		BroadPhaseCollisionComponent broadPhaseCollisionComponent = entityManager.getComponent(entityManager.playerUUID, BroadPhaseCollisionComponent.class);
		List<Collision> collisions = broadPhaseCollisionComponent.collisions;

		for (Collision collision : collisions) {
			collision.resolve();
		}
		collisions.clear();

		BoundingBoxComponent player = entityManager.getComponent(entityManager.playerUUID, BoundingBoxComponent.class);
		VelocityComponent velocityComponent = entityManager.getComponent(entityManager.playerUUID, VelocityComponent.class);
		player.x += velocityComponent.velocity.x;
		player.y += velocityComponent.velocity.y;
		velocityComponent.velocity.x = 0.0f;
	}
}
