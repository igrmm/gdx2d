package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
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

		if (!batchC.dispose) {
			OrthographicCamera camera = cameraC.camera;
			OrthogonalTiledMapRenderer mapRenderer = mapRendererC.mapRenderer;
			SpriteBatch batch = batchC.batch;

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			updateCameraPosition(entityManager, delta);
			camera.update();
			mapRenderer.setView(camera);
			mapRenderer.render();
			batch.setProjectionMatrix(camera.combined);

			batch.begin();
			renderSprites(delta, entityManager, batch);
			renderUI(delta, entityManager, batch, camera);
			batch.end();
		}
	}

	private void renderSprites(float delta, EntityManager entityManager, SpriteBatch batch) {
		Set<String> entitiesPossessingAnimationC =
				entityManager.getAllEntitiesPossessingComponent(AnimationComponent.class);

		for (String entityPossessingAnimationC : entitiesPossessingAnimationC) {
			BoundingBoxComponent bBoxC =
					entityManager.getComponent(entityPossessingAnimationC, BoundingBoxComponent.class);
			AnimationComponent animationC =
					entityManager.getComponent(entityPossessingAnimationC, AnimationComponent.class);

			float scale = animationC.scale;
			float offset = animationC.offset;
			float rotation = animationC.rotation;

			float x = bBoxC.bBox.x - offset * scale;
			float y = bBoxC.bBox.y - offset * scale;

			TextureRegion tex = animationC.getKeyFrame(delta);

			batch.draw(
					tex,
					x,
					y,
					0,
					0,
					tex.getRegionWidth(),
					tex.getRegionHeight(),
					scale,
					scale,
					rotation
			);
		}
	}

	private void renderUI(float delta, EntityManager entityManager, SpriteBatch batch, OrthographicCamera camera) {
		Vector3 camPosition = camera.position;
		float camZoom = camera.zoom;

		Set<String> entitiesPossessingUIAnimationC =
				entityManager.getAllEntitiesPossessingComponent(UIAnimationComponent.class);

		for (String entityPossessingUIAnimationC : entitiesPossessingUIAnimationC) {
			BoundingBoxComponent bBoxC =
					entityManager.getComponent(entityPossessingUIAnimationC, BoundingBoxComponent.class);
			UIAnimationComponent uIAnimationC =
					entityManager.getComponent(entityPossessingUIAnimationC, UIAnimationComponent.class);

			float scale = uIAnimationC.scale;

			float x = camPosition.x - bBoxC.bBox.x * camZoom;
			float y = camPosition.y - bBoxC.bBox.y * camZoom;

			TextureRegion tex = uIAnimationC.getKeyFrame(delta);

			batch.draw(
					tex,
					x,
					y,
					0.0f,
					0.0f,
					tex.getRegionWidth() * camZoom,
					tex.getRegionHeight() * camZoom,
					scale,
					scale,
					0.0f
			);
		}
	}

	private void updateCameraPosition(EntityManager entityManager, float delta) {
		String playerUUID = entityManager.playerUUID;
		BoundingBoxComponent playerBBoxC =
				entityManager.getComponent(playerUUID, BoundingBoxComponent.class);
		Rectangle playerBBox = playerBBoxC.bBox;

		float playerCenterX = playerBBox.x + playerBBox.width / 2.0f;
		float playerCenterY = playerBBox.y + playerBBox.height / 2.0f;

		String coreUUID = entityManager.coreUUID;
		CameraComponent cameraC =
				entityManager.getComponent(coreUUID, CameraComponent.class);
		OrthographicCamera camera = cameraC.camera;

		float mapWidth = cameraC.mapWidth;
		float mapHeight = cameraC.mapHeight;

		/* Make camera follow player */
		float alpha = 0.3f;
		camera.position.x += alpha * (playerCenterX - camera.position.x);
		camera.position.y += alpha * (playerCenterY - camera.position.y);

		/* Limit camera position inside tiled map bounds */
		float minCameraPositionX = camera.viewportWidth / 2.0f * camera.zoom;
		float minCameraPositionY = camera.viewportHeight / 2.0f * camera.zoom;
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
