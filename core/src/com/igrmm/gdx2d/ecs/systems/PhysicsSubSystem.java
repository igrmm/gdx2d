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

			/* acceleration */
			movementC.speed.x += movementC.directionInput * movementC.acceleration;

			/* max speed */
			if (movementC.directionInput != 0)
				movementC.speed.x = Math.abs(movementC.speed.x) < movementC.maxSpeed
						? movementC.speed.x
						: movementC.maxSpeed * movementC.directionInput;

			/* friction */
			if (movementC.directionInput == 0 && movementC.speed.x != 0) {
				if (movementC.speed.x < 0) {
					movementC.speed.x += movementC.friction;
					movementC.speed.x = movementC.speed.x < 0 ? movementC.speed.x : 0;
				} else if (movementC.speed.x > 0) {
					movementC.speed.x -= movementC.friction;
					movementC.speed.x = movementC.speed.x > 0 ? movementC.speed.x : 0;
				}
			}

			/* jumping */
			if (movementC.jumpInput) {
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
