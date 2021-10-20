package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.BroadPhaseCollisionComponent;
import com.igrmm.gdx2d.ecs.components.MovementComponent;

import java.util.*;

public class CollisionSubSystem implements SubSystem {

	@Override
	public void update(Manager manager, float delta) {

		/* Resolve collisions */
		Set<String> entitiesPossessingBPhaseColC =
				manager.getAllEntitiesPossessingComponent(BroadPhaseCollisionComponent.class);

		for (String entityPossessingBPhaseColC : entitiesPossessingBPhaseColC) {
			BroadPhaseCollisionComponent broadPhaseCollisionC =
					manager.getComponent(entityPossessingBPhaseColC, BroadPhaseCollisionComponent.class);

			List<Collision> collisions = broadPhaseCollisionC.collisions;
			Collections.sort(collisions);

			for (Collision collision : collisions) {
				collision.resolve();
				Pools.free(collision);
			}
			collisions.clear();
		}

		/* Make mobile entities move after resolving collisions */
		Set<String> entitiesPossessingMovC =
				manager.getAllEntitiesPossessingComponent(MovementComponent.class);

		for (String entityPossessingMovC : entitiesPossessingMovC) {
			BoundingBoxComponent bBoxC =
					manager.getComponent(entityPossessingMovC, BoundingBoxComponent.class);
			MovementComponent movementC =
					manager.getComponent(entityPossessingMovC, MovementComponent.class);

			Rectangle bBox = bBoxC.bBox;
			Vector2 speed = movementC.speed;

			bBoxC.previousPosition.set(bBox.x, bBox.y);
			bBox.x += speed.x;
			bBox.y += speed.y;
		}
	}
}
