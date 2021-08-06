package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.Set;

public class BlockSubSystem implements SubSystem {
	@Override
	public void update(Manager manager, float delta) {
		Set<String> entitiesPossessingBBoxC =
				manager.getAllEntitiesPossessingComponent(BoundingBoxComponent.class);
		Set<String> entitiesPossessingMovC =
				manager.getAllEntitiesPossessingComponent(MovementComponent.class);

		for (String entityPossessingBBoxC : entitiesPossessingBBoxC) {
			TypeComponent typeC =
					manager.getComponent(entityPossessingBBoxC, TypeComponent.class);
			EntityType entityType = typeC.type;

			if (entityType == EntityType.BLOCK) {
				BoundingBoxComponent staticBBoxC =
						manager.getComponent(entityPossessingBBoxC, BoundingBoxComponent.class);

				for (String entityPossessingMovC : entitiesPossessingMovC) {
					MovementComponent movementC =
							manager.getComponent(entityPossessingMovC, MovementComponent.class);
					BoundingBoxComponent dynamicBBoxC =
							manager.getComponent(entityPossessingMovC, BoundingBoxComponent.class);
					BroadPhaseCollisionComponent broadPhaseCollisionC =
							manager.getComponent(entityPossessingMovC, BroadPhaseCollisionComponent.class);

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
