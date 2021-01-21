package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.CameraComponent;

public class CameraControlSubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		String playerUUID = entityManager.playerUUID;
		BoundingBoxComponent playerBBoxComponent =
				entityManager.getComponent(playerUUID, BoundingBoxComponent.class);
		float playerCenterX = playerBBoxComponent.x + playerBBoxComponent.width / 2.0f;
		float playerCenterY = playerBBoxComponent.y + playerBBoxComponent.height / 2.0f;

		String graphicsUUID = entityManager.graphicsUUID;
		CameraComponent cameraComponent =
				entityManager.getComponent(graphicsUUID, CameraComponent.class);
		OrthographicCamera camera = cameraComponent.camera;
		float mapWidth = cameraComponent.mapWidth;
		float mapHeight = cameraComponent.mapHeight;

		/* Make camera follow player */
		float alpha = 0.3f;
		camera.position.x += alpha * (playerCenterX - camera.position.x);
		camera.position.y += alpha * (playerCenterY - camera.position.y);

		/* Limit camera position inside tiled map bounds */
		float minCameraPositionX = camera.viewportWidth / 2.0f;
		float minCameraPositionY = camera.viewportHeight / 2.0f;
		float maxCameraPositionX = mapWidth - minCameraPositionX;
		float maxCameraPositionY = mapHeight - minCameraPositionY;

		if (mapWidth > camera.viewportWidth)
			camera.position.x = MathUtils.clamp(camera.position.x, minCameraPositionX, maxCameraPositionX);
		else
			camera.position.x = minCameraPositionX;

		if (mapHeight > camera.viewportHeight)
			camera.position.y = MathUtils.clamp(camera.position.y, minCameraPositionY, maxCameraPositionY);
		else
			camera.position.y = minCameraPositionY;
	}
}
