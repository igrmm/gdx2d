package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.BroadPhaseCollisionComponent;
import com.igrmm.gdx2d.ecs.components.MovementComponent;

import java.util.List;
import java.util.Set;

public class CollisionSubSystem implements SubSystem {

	@Override
	public void update(EntityManager entityManager, float delta) {

		/* Resolve collisions */
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

		/* Make mobile entities move after resolving collisions */
		Set<String> entitiesPossessingMovC =
				entityManager.getAllEntitiesPossessingComponent(MovementComponent.class);

		for (String entityPossessingMovC : entitiesPossessingMovC) {
			BoundingBoxComponent bBoxC =
					entityManager.getComponent(entityPossessingMovC, BoundingBoxComponent.class);
			MovementComponent movementC =
					entityManager.getComponent(entityPossessingMovC, MovementComponent.class);

			Rectangle bBox = bBoxC.bBox;
			Vector2 speed = movementC.speed;

			bBox.x += speed.x;
			bBox.y += speed.y;
		}
	}
}
