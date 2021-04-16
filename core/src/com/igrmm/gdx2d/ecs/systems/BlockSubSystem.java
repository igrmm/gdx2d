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
		Set<String> entitiesPossessingVelC =
				entityManager.getAllEntitiesPossessingComponent(VelocityComponent.class);
		Set<String> entitiesPossessingJumpC =
				entityManager.getAllEntitiesPossessingComponent(JumpComponent.class);

		for (String entityPossessingBBoxC : entitiesPossessingBBoxC) {
			TypeComponent typeC =
					entityManager.getComponent(entityPossessingBBoxC, TypeComponent.class);
			EntityType entityType = typeC.type;

			if (entityType == EntityType.BLOCK) {
				BoundingBoxComponent staticBBoxC =
						entityManager.getComponent(entityPossessingBBoxC, BoundingBoxComponent.class);

				for (String entityPossessingVelC : entitiesPossessingVelC) {
					BoundingBoxComponent dynamicBBoxC =
							entityManager.getComponent(entityPossessingVelC, BoundingBoxComponent.class);
					VelocityComponent velocityC =
							entityManager.getComponent(entityPossessingVelC, VelocityComponent.class);
					BroadPhaseCollisionComponent broadPhaseCollisionC =
							entityManager.getComponent(entityPossessingVelC, BroadPhaseCollisionComponent.class);

					Rectangle dynamicBBox = dynamicBBoxC.bBox;
					Rectangle staticBBox = staticBBoxC.bBox;
					Vector2 velocity = velocityC.velocity;

					Collision collision =
							new Collision(dynamicBBox, staticBBox, velocity) {
								@Override
								public boolean resolve() {
									if (super.resolve()) {
										if (getNormalY() > 0) {
											if (entitiesPossessingJumpC.contains(entityPossessingVelC)) {
												JumpComponent jumpC =
														entityManager.getComponent(entityPossessingVelC, JumpComponent.class);
												jumpC.grounded = true;
											}
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
