package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;


public class RenderingSubSystem implements SubSystem {

	@Override
	public void update(EntityManager entityManager, float delta) {
		String graphicsUUID = entityManager.graphicsUUID;
		CameraComponent cameraComponent =
				entityManager.getComponent(graphicsUUID, CameraComponent.class);
		BatchComponent batchComponent =
				entityManager.getComponent(graphicsUUID, BatchComponent.class);
		MapRendererComponent mapRendererComponent =
				entityManager.getComponent(graphicsUUID, MapRendererComponent.class);

		OrthographicCamera camera = cameraComponent.camera;
		OrthogonalTiledMapRenderer mapRenderer = mapRendererComponent.mapRenderer;
		SpriteBatch batch = batchComponent.batch;

		// TEMP PLAYER CODE
		String playerUUID = entityManager.playerUUID;
		PlayerAnimationComponent playerAnimationComponent =
				entityManager.getComponent(playerUUID, PlayerAnimationComponent.class);
		BoundingBoxComponent playerBBox =
				entityManager.getComponent(playerUUID, BoundingBoxComponent.class);
		float playerX = playerBBox.x;
		float playerY = playerBBox.y;

		/* Render map and player */
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		mapRenderer.setView(camera);
		mapRenderer.render();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(
				playerAnimationComponent.getKeyFrame(delta),
				playerX - PlayerAnimationComponent.SPRITE_OFFSET,
				playerY - PlayerAnimationComponent.SPRITE_OFFSET
		);
		batch.end();
	}
}
