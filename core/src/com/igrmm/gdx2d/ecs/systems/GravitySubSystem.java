package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.AccelerationComponent;
import com.igrmm.gdx2d.ecs.components.GravityComponent;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

import java.util.Set;

public class GravitySubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		Set<String> entities = entityManager.getAllEntitiesPossessingComponent(GravityComponent.class);
		for (String entity : entities) {
			AccelerationComponent accelerationComponent =
					entityManager.getComponent(entity, AccelerationComponent.class);
			VelocityComponent velocityComponent =
					entityManager.getComponent(entity, VelocityComponent.class);

			accelerationComponent.acceleration.y = GravityComponent.GRAVITY;
			velocityComponent.velocity.y += accelerationComponent.acceleration.y;
		}
	}
}
