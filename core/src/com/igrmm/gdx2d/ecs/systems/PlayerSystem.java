package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.Components;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.InputComponent;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

public class PlayerSystem implements System {

	@Override
	public void update(Components components) {
		InputComponent inputs = components.inputComponent;
		BoundingBoxComponent boundingBoxComponent = components.dynamicBoundingBoxComponents.get(components.playerID);
		VelocityComponent velocityComponent = components.velocityComponents.get(components.playerID);

		//TEMP PLAYER CODE
		BoundingBoxComponent bBox = components.dynamicBoundingBoxComponents.get(components.playerID);

		if (inputs.right)
			velocityComponent.velocity.x = velocityComponent.maxVelocity.x;


		if (inputs.left)
			velocityComponent.velocity.x = velocityComponent.maxVelocity.x * -1.0f;


		if (inputs.up) {
			velocityComponent.velocity.y = velocityComponent.maxVelocity.y;
		}

		if (inputs.down) {
			velocityComponent.velocity.y = velocityComponent.maxVelocity.y * -1.0f;
		}
	}
}
