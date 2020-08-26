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
			bBox.oldX = bBox.x;
			bBox.x += 5;
		}

		if (inputs.left) {
			bBox.oldX = bBox.x;
			bBox.x -= 5;
		}

		if (inputs.up) {
			bBox.oldY = bBox.y;
			bBox.y += 5;
		}

		if (inputs.down) {
			bBox.oldY = bBox.y;
			bBox.y -= 5;
		}
	}
}
