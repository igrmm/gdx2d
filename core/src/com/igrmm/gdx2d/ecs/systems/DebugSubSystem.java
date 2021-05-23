package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;

public class DebugSubSystem implements SubSystem {
	//measure velocity in pixels per second
	private long velocityTime = System.currentTimeMillis();
	private float averageVelocity = 0.0f;

	private float playerLastX = 0.0f;

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
			fontC.font.getData().setScale(2.0f * cameraC.camera.zoom);

			String playerUUID = entityManager.playerUUID;
			BoundingBoxComponent playerBBoxC =
					entityManager.getComponent(playerUUID, BoundingBoxComponent.class);

			//measure velocity in pixels per second
			if (System.currentTimeMillis() - velocityTime > 1000) {
				averageVelocity =
						Math.abs(playerBBoxC.bBox.x - playerLastX) / (System.currentTimeMillis() - velocityTime) * 1000;
				averageVelocity = (float) Math.ceil(averageVelocity);
				playerLastX = playerBBoxC.bBox.x;
				velocityTime = System.currentTimeMillis();
			}

			//debug info
			fontC.text = fontC.text.concat("FPS: " + Gdx.graphics.getFramesPerSecond() + "\n");
			fontC.text = fontC.text.concat("DPI: " + Gdx.graphics.getDensity() + "\n");
			fontC.text = fontC.text.concat("RES: " + Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight() + "\n");
			fontC.text = fontC.text.concat("CAMZOOM: " + cameraC.camera.zoom + "\n");
			fontC.text = fontC.text.concat("AVERAGE VELOCITY: " + averageVelocity + "\n");

			batchC.batch.begin();
			fontC.font.draw(
					batchC.batch,
					fontC.text,
					cameraC.camera.position.x - cameraC.camera.viewportWidth / 2.0f * cameraC.camera.zoom,
					cameraC.camera.position.y + cameraC.camera.viewportHeight / 2.0f * cameraC.camera.zoom
			);
			batchC.batch.end();
			fontC.text = "";
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
