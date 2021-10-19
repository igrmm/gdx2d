package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.utils.Pools;
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
				for (String entityPossessingMovC : entitiesPossessingMovC) {
					BroadPhaseCollisionComponent broadPhaseCollisionC =
							manager.getComponent(entityPossessingMovC, BroadPhaseCollisionComponent.class);

					BlockCollision collision = Pools.obtain(BlockCollision.class);
					collision.init(entityPossessingMovC, entityPossessingBBoxC, manager);
					broadPhaseCollisionC.collisions.add(collision);
				}
			}
		}
	}

	static class BlockCollision extends Collision {
		MovementComponent movementC;

		@Override
		public void init(String dynamicEntity, String staticEntity, Manager manager) {
			super.init(dynamicEntity, staticEntity, manager);
			this.movementC = manager.getComponent(dynamicEntity, MovementComponent.class);
			this.movementC.grounded = false;
		}

		@Override
		public void reset() {
			super.reset();
			movementC = null;
		}

		@Override
		public boolean resolve() {
			if (super.resolve()) {
				if (getNormalY() > 0) {
					movementC.grounded = true;
				}
				return true;
			} else return false;
		}
	}
}
