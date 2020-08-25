package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.Components;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.InputComponent;

public class PlayerSystem implements System {

	@Override
	public void update(Components components) {
		InputComponent inputs = components.inputComponent;

		//TEMP PLAYER CODE
		BoundingBoxComponent bBox = components.dynamicBoundingBoxComponents.get(components.playerID);

		if (inputs.right) {
			bBox.x += 5;
		}

		if (inputs.left) {
			bBox.x -= 5;
		}

		if (inputs.up) {
			bBox.y += 5;
		}

		if (inputs.down) {
			bBox.y -= 5;
		}
	}
}
