package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.Set;

public class BlockSubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {

		Set<String> blocks = entityManager.getAllEntitiesPossessingComponent(BoundingBoxComponent.class);
		Set<String> mobiles = entityManager.getAllEntitiesPossessingComponent(VelocityComponent.class);
		Set<String> jumpers = entityManager.getAllEntitiesPossessingComponent(JumpComponent.class);

		for (String block : blocks) {
			TypeComponent typeComponent = entityManager.getComponent(block, TypeComponent.class);
			if (typeComponent.type == EntityType.BLOCK) {
				BoundingBoxComponent staticBBoxComponent =
						entityManager.getComponent(block, BoundingBoxComponent.class);

				for (String mobile : mobiles) {
					BoundingBoxComponent dynamicBBoxComponent =
							entityManager.getComponent(mobile, BoundingBoxComponent.class);
					VelocityComponent velocityComponent =
							entityManager.getComponent(mobile, VelocityComponent.class);
					BroadPhaseCollisionComponent broadPhaseCollisionComponent =
							entityManager.getComponent(mobile, BroadPhaseCollisionComponent.class);

					Rectangle dynamicBBox = dynamicBBoxComponent.bBox;
					Rectangle staticBBox = staticBBoxComponent.bBox;

					Collision collision =
							new Collision(dynamicBBox, staticBBox, velocityComponent.velocity) {
								@Override
								public boolean resolve() {
									if (super.resolve()) {
										if (getNormalY() > 0) {
											if (jumpers.contains(mobile)) {
												JumpComponent jumpComponent =
														entityManager.getComponent(mobile, JumpComponent.class);
												jumpComponent.grounded = true;
											}
										}
										return true;
									} else return false;
								}
							};
					broadPhaseCollisionComponent.collisions.add(collision);
				}
			}
		}
	}
}
