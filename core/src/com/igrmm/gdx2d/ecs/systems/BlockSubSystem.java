package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.*;

public class BlockSubSystem implements SubSystem {
	private final Rectangle collisionArea = new Rectangle();
	private final Rectangle activeZone = new Rectangle();
	private final Map<String, Rectangle> staticBBoxes = new HashMap<>();

	private void computeCollisionArea(Rectangle dynamicRect, Vector2 speed) {
		if (speed.x > 0.0f) {
			collisionArea.x = dynamicRect.x;
			collisionArea.width = dynamicRect.width + speed.x;
		} else {
			collisionArea.x = dynamicRect.x + speed.x;
			collisionArea.width = dynamicRect.width - speed.x;
		}

		if (speed.y > 0.0f) {
			collisionArea.y = dynamicRect.y;
			collisionArea.height = dynamicRect.height + speed.y;
		} else {
			collisionArea.y = dynamicRect.y + speed.y;
			collisionArea.height = dynamicRect.height - speed.y;
		}
	}

	private void filterBoundingBoxes(Manager manager) {
		Set<String> entitiesPossessingBBoxC =
				manager.getAllEntitiesPossessingComponent(BoundingBoxComponent.class);
		BoundingBoxComponent playerBBoxC =
				manager.getComponent(manager.playerUUID, BoundingBoxComponent.class);

		staticBBoxes.clear();

		activeZone.set(
				playerBBoxC.bBox.x + playerBBoxC.bBox.width / 2.0f - Gdx.graphics.getWidth() / 2.0f,
				playerBBoxC.bBox.y + playerBBoxC.bBox.height / 2.0f - Gdx.graphics.getHeight() / 2.0f,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()
		);

		for (String entityPossessingBBoxC : entitiesPossessingBBoxC) {
			TypeComponent typeC =
					manager.getComponent(entityPossessingBBoxC, TypeComponent.class);
			EntityType entityType = typeC.type;

			if (entityType == EntityType.BLOCK) {
				BoundingBoxComponent staticBBoxC =
						manager.getComponent(entityPossessingBBoxC, BoundingBoxComponent.class);

				if (activeZone.overlaps(staticBBoxC.bBox))
					staticBBoxes.put(entityPossessingBBoxC, staticBBoxC.bBox);
			}
		}
	}

	@Override
	public void update(Manager manager, float delta) {
		filterBoundingBoxes(manager);

		Set<String> entitiesPossessingMovC =
				manager.getAllEntitiesPossessingComponent(MovementComponent.class);

		for (String entityPossessingMovC : entitiesPossessingMovC) {
			MovementComponent movementC =
					manager.getComponent(entityPossessingMovC, MovementComponent.class);
			movementC.grounded = false;

			if (movementC.speed.x != 0 || movementC.speed.y != 0) {
				BroadPhaseCollisionComponent broadPhaseCollisionC =
						manager.getComponent(entityPossessingMovC, BroadPhaseCollisionComponent.class);
				BoundingBoxComponent dynamicBBoxC =
						manager.getComponent(entityPossessingMovC, BoundingBoxComponent.class);
				computeCollisionArea(dynamicBBoxC.bBox, movementC.speed);

				for (String entityPossessingBBox : staticBBoxes.keySet()) {
					Rectangle staticBBox = staticBBoxes.get(entityPossessingBBox);
					if (collisionArea.overlaps(staticBBox)) {
						BlockCollision collision = Pools.obtain(BlockCollision.class);
						collision.init(entityPossessingMovC, entityPossessingBBox, manager);
						broadPhaseCollisionC.collisions.add(collision);
					}
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
