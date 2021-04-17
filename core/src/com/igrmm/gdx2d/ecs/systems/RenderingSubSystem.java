package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;

import java.util.Set;

public class RenderingSubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		String coreUUID = entityManager.coreUUID;
		CameraComponent cameraC =
				entityManager.getComponent(coreUUID, CameraComponent.class);
		BatchComponent batchC =
				entityManager.getComponent(coreUUID, BatchComponent.class);
		MapRendererComponent mapRendererC =
				entityManager.getComponent(coreUUID, MapRendererComponent.class);

		OrthographicCamera camera = cameraC.camera;
		OrthogonalTiledMapRenderer mapRenderer = mapRendererC.mapRenderer;
		SpriteBatch batch = batchC.batch;

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		mapRenderer.setView(camera);
		mapRenderer.render();
		batch.setProjectionMatrix(camera.combined);

		/* Render all sprites */
		batch.begin();
		Set<String> entitiesPossessingKeyFrameC =
				entityManager.getAllEntitiesPossessingComponent(KeyFrameComponent.class);

		for (String entityPossessingKeyFrameC : entitiesPossessingKeyFrameC) {
			KeyFrameComponent keyFrameC =
					entityManager.getComponent(entityPossessingKeyFrameC, KeyFrameComponent.class);
			BoundingBoxComponent bBoxC =
					entityManager.getComponent(entityPossessingKeyFrameC, BoundingBoxComponent.class);

			Rectangle bBox = bBoxC.bBox;

			batch.draw(
					keyFrameC.getKeyFrame(),
					keyFrameC.getX(bBox.x),
					keyFrameC.getY(bBox.y)
			);
		}
		batch.end();
	}
}
