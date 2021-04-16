package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.AccelerationComponent;
import com.igrmm.gdx2d.ecs.components.GravityComponent;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

import java.util.Set;

public class GravitySubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		Set<String> entitiesPossessingGravityC =
				entityManager.getAllEntitiesPossessingComponent(GravityComponent.class);

		for (String entityPossessingGravityC : entitiesPossessingGravityC) {
			AccelerationComponent accelerationC =
					entityManager.getComponent(entityPossessingGravityC, AccelerationComponent.class);
			VelocityComponent velocityC =
					entityManager.getComponent(entityPossessingGravityC, VelocityComponent.class);

			accelerationC.acceleration.y = GravityComponent.GRAVITY;
			velocityC.velocity.y += accelerationC.acceleration.y;
		}
	}
}
