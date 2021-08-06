package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.MovementComponent;

import java.util.Set;

public class PhysicsSubSystem implements SubSystem {
	@Override
	public void update(Manager manager, float delta) {
		Set<String> entitiesPossessingMovC =
				manager.getAllEntitiesPossessingComponent(MovementComponent.class);

		for (String entityPossessingMovC : entitiesPossessingMovC) {
			MovementComponent movementC =
					manager.getComponent(entityPossessingMovC, MovementComponent.class);

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
				if (movementC.grounded && !movementC.jumping) {
					movementC.jumpTimer = movementC.jumpTime;
				}
				movementC.jumping = true;
			} else if (movementC.grounded) movementC.jumping = false;

			if (movementC.jumpTimer > 0.0f) {
				movementC.speed.y = movementC.jumpForce;
				movementC.jumpTimer -= delta;
				if (!movementC.jumpInput) movementC.jumpTimer = 0.0f;
			}

			/* Gravity */
			float gravity = movementC.gravity;
			if (movementC.jumping && movementC.speed.y <= 0.0f) gravity *= 2.0f;
			movementC.speed.y += gravity;
		}
	}
}
