package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.MovementComponent;

import java.util.Set;

public class PhysicsSubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		Set<String> entitiesPossessingMovC =
				entityManager.getAllEntitiesPossessingComponent(MovementComponent.class);

		for (String entityPossessingMovC : entitiesPossessingMovC) {
			MovementComponent movementC =
					entityManager.getComponent(entityPossessingMovC, MovementComponent.class);

			movementC.speed.y += movementC.gravity;
		}
	}
}
