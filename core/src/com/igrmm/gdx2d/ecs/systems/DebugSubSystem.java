package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.CameraComponent;
import com.igrmm.gdx2d.ecs.components.ShapeRendererComponent;

public class DebugSubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		Gdx.graphics.setTitle(Gdx.graphics.getFramesPerSecond() + "");
		String coreUUID = entityManager.coreUUID;
		ShapeRendererComponent shapeRendererC =
				entityManager.getComponent(coreUUID, ShapeRendererComponent.class);
		CameraComponent cameraC =
				entityManager.getComponent(coreUUID, CameraComponent.class);

		if (!shapeRendererC.dispose) {
			ShapeRenderer shapeRenderer = shapeRendererC.shapeRenderer;
			OrthographicCamera camera = cameraC.camera;

			String playerUUID = entityManager.playerUUID;
			BoundingBoxComponent playerBBoxC =
					entityManager.getComponent(playerUUID, BoundingBoxComponent.class);

			Rectangle playerBBox = playerBBoxC.bBox;

			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.rect(playerBBox.x, playerBBox.y, playerBBox.width, playerBBox.height);
			shapeRenderer.end();
		}
	}
}
