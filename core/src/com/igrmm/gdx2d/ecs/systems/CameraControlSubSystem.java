package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.CameraComponent;

public class CameraControlSubSystem implements SubSystem {
	@Override
	public void update(Manager manager, float delta) {

		// Camera will lerp player center position

		BoundingBoxComponent playerBBoxC =
				manager.getComponent(manager.playerUUID, BoundingBoxComponent.class);
		CameraComponent cameraC =
				manager.getComponent(manager.coreUUID, CameraComponent.class);

		float playerCenterX = playerBBoxC.bBox.x + playerBBoxC.bBox.width / 2.0f;
		float playerCenterY = playerBBoxC.bBox.y + playerBBoxC.bBox.height / 2.0f;

		cameraC.previousPosition.set(cameraC.position);
		float alpha = 0.05f;
		cameraC.position.x = playerCenterX * alpha + cameraC.position.x * (1.0f - alpha);
		cameraC.position.y = playerCenterY * alpha + cameraC.position.y * (1.0f - alpha);
	}
}
