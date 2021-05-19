package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;

public class DebugSubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		Gdx.graphics.setTitle(Gdx.graphics.getFramesPerSecond() + "");
		String coreUUID = entityManager.coreUUID;
		ShapeRendererComponent shapeRendererC =
				entityManager.getComponent(coreUUID, ShapeRendererComponent.class);
		CameraComponent cameraC =
				entityManager.getComponent(coreUUID, CameraComponent.class);
		BatchComponent batchC =
				entityManager.getComponent(coreUUID, BatchComponent.class);
		FontComponent fontC =
				entityManager.getComponent(coreUUID, FontComponent.class);

		if (!batchC.dispose) {
			batchC.batch.begin();
			fontC.font.draw(
					batchC.batch,
					"FPS: " + Gdx.graphics.getFramesPerSecond(),
					cameraC.camera.position.x - Gdx.graphics.getWidth() / 2.0f,
					cameraC.camera.position.y + Gdx.graphics.getHeight() / 2.0f
			);
			batchC.batch.end();
		}

		if (!shapeRendererC.dispose) {
//			ShapeRenderer shapeRenderer = shapeRendererC.shapeRenderer;
//			OrthographicCamera camera = cameraC.camera;
//
//			String playerUUID = entityManager.playerUUID;
//			BoundingBoxComponent playerBBoxC =
//					entityManager.getComponent(playerUUID, BoundingBoxComponent.class);
//
//			Rectangle playerBBox = playerBBoxC.bBox;
//
//			shapeRenderer.setProjectionMatrix(camera.combined);
//			shapeRenderer.setColor(Color.RED);
//			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//			shapeRenderer.rect(playerBBox.x, playerBBox.y, playerBBox.width, playerBBox.height);
//			shapeRenderer.end();
		}
	}
}
