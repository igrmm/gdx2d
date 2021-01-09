package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.Collision;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.BroadPhaseCollisionComponent;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

import java.util.List;

public class BlockSystem implements System {
	@Override
	public void update(EntityManager entityManager, float delta) {

		List<BoundingBoxComponent> blocks = entityManager.getAllComponentsOfType(BoundingBoxComponent.class);

		//TEMP PLAYER CODE
		BoundingBoxComponent player = entityManager.getComponent(entityManager.playerUUID, BoundingBoxComponent.class);
		VelocityComponent velocityComponent = entityManager.getComponent(entityManager.playerUUID, VelocityComponent.class);
		BroadPhaseCollisionComponent broadPhaseCollisionComponent = entityManager.getComponent(entityManager.playerUUID, BroadPhaseCollisionComponent.class);
		for (BoundingBoxComponent blockBBox : blocks) {
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
