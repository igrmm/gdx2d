package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.InputComponent;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

public class PlayerSystem implements System {

	@Override
	public void update(EntityManager entityManager, float delta) {
		InputComponent inputComponent = entityManager.getComponent(entityManager.playerUUID, InputComponent.class);
		VelocityComponent velocityComponent = entityManager.getComponent(entityManager.playerUUID, VelocityComponent.class);

		if (inputComponent.right)
			velocityComponent.velocity.x = velocityComponent.maxVelocity.x;


		if (inputComponent.left)
			velocityComponent.velocity.x = velocityComponent.maxVelocity.x * -1.0f;


		if (inputComponent.up) {
			velocityComponent.velocity.y = velocityComponent.maxVelocity.y;
		}

		if (inputComponent.down) {
			velocityComponent.velocity.y = velocityComponent.maxVelocity.y * -1.0f;
		}
	}
}
