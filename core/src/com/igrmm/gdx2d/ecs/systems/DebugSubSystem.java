package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;

import java.util.Set;

public class DebugSubSystem implements SubSystem {
	private final AverageVelocity averageVelocity = new AverageVelocity();

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

			averageVelocity.update(playerBBoxC.bBox.x, playerBBoxC.bBox.y);

			//debug info
			fontC.text = fontC.text.concat("FPS: " + Gdx.graphics.getFramesPerSecond() + "\n");
			fontC.text = fontC.text.concat("DPI: " + Gdx.graphics.getDensity() + "\n");
			fontC.text = fontC.text.concat("RES: " + Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight() + "\n");
			fontC.text = fontC.text.concat("CAMZOOM: " + cameraC.camera.zoom + "\n");
			fontC.text = fontC.text.concat("AVERAGE VELOCITY: " + averageVelocity.get() + "\n");
			fontC.text = fontC.text.concat("Tile Size: " + pixelSizeCm(cameraC.camera.zoom) * 32 + "\n");

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
			shapeRendererC.shapeRenderer.setProjectionMatrix(cameraC.camera.combined);
			shapeRendererC.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			renderBoundingBoxes(entityManager, shapeRendererC.shapeRenderer);
			shapeRendererC.shapeRenderer.end();
		}
	}

	private void renderBoundingBoxes(EntityManager entityManager, ShapeRenderer shapeRenderer) {
		Set<String> entitiesPossessingBBoxC =
				entityManager.getAllEntitiesPossessingComponent(BoundingBoxComponent.class);

		for (String entityPossessingBBoxC : entitiesPossessingBBoxC) {
			BoundingBoxComponent bBoxC =
					entityManager.getComponent(entityPossessingBBoxC, BoundingBoxComponent.class);

			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(bBoxC.bBox.x, bBoxC.bBox.y, bBoxC.bBox.width, bBoxC.bBox.height);
		}
	}

	private float pixelSizeCm(float cameraZoom) {
		float DENSITY_MEDIUM = 160.0f;
		float DPI = Gdx.graphics.getDensity() * DENSITY_MEDIUM;
		float INCH_TO_CM = 2.54f;
		return INCH_TO_CM / (DPI * cameraZoom);
	}

	private static final class AverageVelocity {
		public long time0 = System.currentTimeMillis();
		public Vector2 lastPosition = new Vector2();
		public Vector2 averageVelocity = new Vector2();

		public void update(float positionX, float positionY) {
			long timeElapsed = System.currentTimeMillis() - time0;
			if (timeElapsed > 1000) {
				averageVelocity.x =
						(int) (Math.abs(positionX - lastPosition.x) / (System.currentTimeMillis() - time0) * 1000);
				lastPosition.x = positionX;
				averageVelocity.y =
						(int) (Math.abs(positionY - lastPosition.y) / (System.currentTimeMillis() - time0) * 1000);
				lastPosition.y = positionY;

				time0 = System.currentTimeMillis();
			}
		}

		public Vector2 get() {
			return averageVelocity;
		}
	}
}
