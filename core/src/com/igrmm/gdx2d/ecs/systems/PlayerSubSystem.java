package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.ecs.entities.Bullet;


public class PlayerSubSystem implements SubSystem {
	private int facingDirection = MovementComponent.RIGHT_DIRECTION;

	@Override
	public void update(Manager manager, float delta) {
		String playerUUID = manager.playerUUID;
		InputComponent inputC =
				manager.getComponent(playerUUID, InputComponent.class);
		MovementComponent movementC =
				manager.getComponent(playerUUID, MovementComponent.class);
		AnimationComponent playerAnimationC =
				manager.getComponent(playerUUID, AnimationComponent.class);

		movementC.directionInput = inputC.directionInput;
		movementC.jumpInput = inputC.jumpInput;

		if (inputC.directionInput != 0) facingDirection = inputC.directionInput;

		if (inputC.actionInput) Bullet.spawn(facingDirection, manager);

		/* ANIMATIONS */
		if (facingDirection == MovementComponent.RIGHT_DIRECTION) {
			if (Math.abs(movementC.speed.x) > 0.9f * movementC.maxSpeed * Manager.FIXED_TIMESTEP && movementC.grounded)
				playerAnimationC.setAnimation("walk_right");
			else
				playerAnimationC.setAnimation("idle_right");
		}

		if (facingDirection == MovementComponent.LEFT_DIRECTION) {
			if (Math.abs(movementC.speed.x) > 0.9f * movementC.maxSpeed * Manager.FIXED_TIMESTEP && movementC.grounded)
				playerAnimationC.setAnimation("walk_left");
			else
				playerAnimationC.setAnimation("idle_left");
		}
	}
}
