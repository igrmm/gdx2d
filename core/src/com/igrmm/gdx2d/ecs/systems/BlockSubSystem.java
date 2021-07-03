package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.Set;

public class BlockSubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		Set<String> entitiesPossessingBBoxC =
				entityManager.getAllEntitiesPossessingComponent(BoundingBoxComponent.class);
		Set<String> entitiesPossessingMovC =
				entityManager.getAllEntitiesPossessingComponent(MovementComponent.class);

		for (String entityPossessingBBoxC : entitiesPossessingBBoxC) {
			TypeComponent typeC =
					entityManager.getComponent(entityPossessingBBoxC, TypeComponent.class);
			EntityType entityType = typeC.type;

			if (entityType == EntityType.BLOCK) {
				BoundingBoxComponent staticBBoxC =
						entityManager.getComponent(entityPossessingBBoxC, BoundingBoxComponent.class);

				for (String entityPossessingMovC : entitiesPossessingMovC) {
					MovementComponent movementC =
							entityManager.getComponent(entityPossessingMovC, MovementComponent.class);
					BoundingBoxComponent dynamicBBoxC =
							entityManager.getComponent(entityPossessingMovC, BoundingBoxComponent.class);
					BroadPhaseCollisionComponent broadPhaseCollisionC =
							entityManager.getComponent(entityPossessingMovC, BroadPhaseCollisionComponent.class);

					movementC.grounded = false;
					Vector2 speed = movementC.speed;
					Rectangle dynamicBBox = dynamicBBoxC.bBox;
					Rectangle staticBBox = staticBBoxC.bBox;

					Collision collision =
							new Collision(dynamicBBox, staticBBox, speed) {
								@Override
								public boolean resolve() {
									if (super.resolve()) {
										if (getNormalY() > 0) {
											movementC.grounded = true;
										}
										return true;
									} else return false;
								}
							};
					broadPhaseCollisionC.collisions.add(collision);
				}
			}
		}
	}
}
