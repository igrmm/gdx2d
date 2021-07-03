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

			/* horizontal movement */
			movementC.speed.x = movementC.direction * movementC.maxSpeed;

			/* jumping */
			if (movementC.jumped) {
				if (movementC.grounded) {
					if (!movementC.jumping) {
						movementC.speed.y += movementC.jumpForce;
						movementC.jumping = true;
					}
				} else movementC.jumping = true;
			} else if (movementC.grounded) movementC.jumping = false;

			/* Gravity */
			movementC.speed.y += movementC.gravity;
		}
	}
}
