package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.BroadPhaseCollisionComponent;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

import java.util.HashMap;

public class BlockSystem implements System {
	@Override
	public void update(EntityManager entityManager) {

		HashMap<String, BoundingBoxComponent> blocks = components.blockBoundingBoxComponents;

		//TEMP PLAYER CODE
		BoundingBoxComponent player = components.dynamicBoundingBoxComponents.get(components.playerID);
		VelocityComponent velocityComponent = components.velocityComponents.get(components.playerID);
		BroadPhaseCollisionComponent broadPhaseCollisionComponent = components.broadPhaseCollisionComponents.get(components.playerID);
		for (BoundingBoxComponent blockBBox : blocks.values()) {
			Collision collision = new Collision(player, blockBBox, velocityComponent.velocity) {
				@Override
				public boolean resolve() {
					return super.resolve();
				}
			};
			broadPhaseCollisionComponent.collisions.add(collision);
		}
	}
}
