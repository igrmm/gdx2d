package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.InputComponent;
import com.igrmm.gdx2d.ecs.components.PlayerAnimationComponent;
import com.igrmm.gdx2d.ecs.components.PlayerAnimationComponent.PlayerAnimation;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

public class PlayerSystem implements System {

	@Override
	public void update(EntityManager entityManager, float delta) {
		InputComponent inputComponent = entityManager.getComponent(entityManager.playerUUID, InputComponent.class);
		VelocityComponent velocityComponent = entityManager.getComponent(entityManager.playerUUID, VelocityComponent.class);
		PlayerAnimationComponent animationComponent =
				entityManager.getComponent(entityManager.playerUUID, PlayerAnimationComponent.class);

		if (inputComponent.right) {
			velocityComponent.velocity.x = velocityComponent.maxVelocity.x;
			animationComponent.setAnimation(PlayerAnimation.WALK_RIGHT);
		}

		if (inputComponent.left) {
			velocityComponent.velocity.x = velocityComponent.maxVelocity.x * -1.0f;
			animationComponent.setAnimation(PlayerAnimation.WALK_LEFT);
		}

		if (inputComponent.up) {
			velocityComponent.velocity.y = velocityComponent.maxVelocity.y;
		}

		if (inputComponent.down) {
			velocityComponent.velocity.y = velocityComponent.maxVelocity.y * -1.0f;
		}

		if (velocityComponent.velocity.x == 0 && velocityComponent.velocity.y == 0)
			animationComponent.setAnimation(PlayerAnimationComponent.PlayerAnimation.IDLE);
	}
}
