package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;

import java.util.Set;

public class RenderingSubSystem implements SubSystem {

	@Override
	public void update(EntityManager entityManager, float delta) {
		String coreUUID = entityManager.coreUUID;
		CameraComponent cameraComponent =
				entityManager.getComponent(coreUUID, CameraComponent.class);
		BatchComponent batchComponent =
				entityManager.getComponent(coreUUID, BatchComponent.class);
		MapRendererComponent mapRendererComponent =
				entityManager.getComponent(coreUUID, MapRendererComponent.class);

		OrthographicCamera camera = cameraComponent.camera;
		OrthogonalTiledMapRenderer mapRenderer = mapRendererComponent.mapRenderer;
		SpriteBatch batch = batchComponent.batch;

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		mapRenderer.setView(camera);
		mapRenderer.render();
		batch.setProjectionMatrix(camera.combined);

		/* Render all sprites */
		batch.begin();
		Set<String> haveKeyFrameComponents =
				entityManager.getAllEntitiesPossessingComponent(KeyFrameComponent.class);
		for (String hasKeyFrameComponent : haveKeyFrameComponents) {
			KeyFrameComponent keyFrameComponent =
					entityManager.getComponent(hasKeyFrameComponent, KeyFrameComponent.class);
			BoundingBoxComponent boundingBoxComponent =
					entityManager.getComponent(hasKeyFrameComponent, BoundingBoxComponent.class);
			batch.draw(
					keyFrameComponent.getKeyFrame(),
					keyFrameComponent.getX(boundingBoxComponent.x),
					keyFrameComponent.getY(boundingBoxComponent.y)
			);
		}
		batch.end();
	}
}
