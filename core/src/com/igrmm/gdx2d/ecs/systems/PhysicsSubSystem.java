package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.BroadPhaseCollisionComponent;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

import java.util.List;
import java.util.Set;

public class PhysicsSubSystem implements SubSystem {

	@Override
	public void update(EntityManager entityManager, float delta) {
		Set<String> entitiesPossessingBPhaseColC =
				entityManager.getAllEntitiesPossessingComponent(BroadPhaseCollisionComponent.class);

		for (String entityPossessingBPhaseColC : entitiesPossessingBPhaseColC) {
			BroadPhaseCollisionComponent broadPhaseCollisionC =
					entityManager.getComponent(entityPossessingBPhaseColC, BroadPhaseCollisionComponent.class);

			List<Collision> collisions = broadPhaseCollisionC.collisions;

			for (Collision collision : collisions) {
				collision.resolve();
			}
			collisions.clear();
		}

		Set<String> entitiesPossessingVelC =
				entityManager.getAllEntitiesPossessingComponent(VelocityComponent.class);

		for (String entityPossessingVelC : entitiesPossessingVelC) {
			BoundingBoxComponent bBoxC =
					entityManager.getComponent(entityPossessingVelC, BoundingBoxComponent.class);
			VelocityComponent velocityC =
					entityManager.getComponent(entityPossessingVelC, VelocityComponent.class);

			Rectangle bBox = bBoxC.bBox;
			Vector2 velocity = velocityC.velocity;

			bBox.x += velocity.x;
			bBox.y += velocity.y;
			velocity.x = 0.0f;
		}
	}
}
