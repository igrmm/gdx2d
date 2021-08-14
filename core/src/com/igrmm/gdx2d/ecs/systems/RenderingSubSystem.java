package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;

import java.util.Set;

public class RenderingSubSystem implements SubSystem {
	@Override
	public void update(Manager manager, float delta) {
		String coreUUID = manager.coreUUID;
		CameraComponent cameraC =
				manager.getComponent(coreUUID, CameraComponent.class);
		BatchComponent batchC =
				manager.getComponent(coreUUID, BatchComponent.class);
		MapRendererComponent mapRendererC =
				manager.getComponent(coreUUID, MapRendererComponent.class);

		if (!batchC.dispose) {
			OrthographicCamera camera = cameraC.camera;
			OrthogonalTiledMapRenderer mapRenderer = mapRendererC.mapRenderer;
			SpriteBatch batch = batchC.batch;

			interpolateDrawingPositions(manager);

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			updateCameraPosition(manager, delta);
			camera.update();
			mapRenderer.setView(camera);
			mapRenderer.render();
			batch.setProjectionMatrix(camera.combined);

			batch.begin();
			renderSprites(delta, manager, batch);
			renderUI(delta, manager, batch, camera);
			batch.end();
		}
	}

	private void interpolateDrawingPositions(Manager manager) {
		Set<String> entitiesPossessingAnimationC =
				manager.getAllEntitiesPossessingComponent(AnimationComponent.class);

		for (String entityPossessingAnimationC : entitiesPossessingAnimationC) {
			BoundingBoxComponent bBoxC =
					manager.getComponent(entityPossessingAnimationC, BoundingBoxComponent.class);
			AnimationComponent animationC =
					manager.getComponent(entityPossessingAnimationC, AnimationComponent.class);

			float alpha = manager.getAlpha();
			animationC.drawingPosition.x = Interpolation.smooth.apply(bBoxC.previousPosition.x, bBoxC.bBox.x, alpha);
			animationC.drawingPosition.y = Interpolation.smooth.apply(bBoxC.previousPosition.y, bBoxC.bBox.y, alpha);
		}
	}

	private void renderSprites(float delta, Manager manager, SpriteBatch batch) {
		Set<String> entitiesPossessingAnimationC =
				manager.getAllEntitiesPossessingComponent(AnimationComponent.class);

		for (String entityPossessingAnimationC : entitiesPossessingAnimationC) {
			AnimationComponent animationC =
					manager.getComponent(entityPossessingAnimationC, AnimationComponent.class);

			float scale = animationC.scale;
			float offset = animationC.offset;
			float rotation = animationC.rotation;

//			float x = Math.round(animationC.drawingPosition.x - offset * scale);
//			float y = Math.round(animationC.drawingPosition.y - offset * scale);
			float x = animationC.drawingPosition.x - offset * scale;
			float y = animationC.drawingPosition.y - offset * scale;

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

	private void renderUI(float delta, Manager manager, SpriteBatch batch, OrthographicCamera camera) {
		Vector3 camPosition = camera.position;
		float camZoom = camera.zoom;

		Set<String> entitiesPossessingUIAnimationC =
				manager.getAllEntitiesPossessingComponent(UIAnimationComponent.class);

		for (String entityPossessingUIAnimationC : entitiesPossessingUIAnimationC) {
			BoundingBoxComponent bBoxC =
					manager.getComponent(entityPossessingUIAnimationC, BoundingBoxComponent.class);
			UIAnimationComponent uIAnimationC =
					manager.getComponent(entityPossessingUIAnimationC, UIAnimationComponent.class);

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

	private void updateCameraPosition(Manager manager, float delta) {
		String playerUUID = manager.playerUUID;
		BoundingBoxComponent playerBBoxC =
				manager.getComponent(playerUUID, BoundingBoxComponent.class);
		AnimationComponent playerAnimationC =
				manager.getComponent(playerUUID, AnimationComponent.class);
		Rectangle playerBBox = playerBBoxC.bBox;

		float playerCenterX = playerAnimationC.drawingPosition.x + playerBBox.width / 2.0f;
		float playerCenterY = playerAnimationC.drawingPosition.y + playerBBox.height / 2.0f;

		String coreUUID = manager.coreUUID;
		CameraComponent cameraC =
				manager.getComponent(coreUUID, CameraComponent.class);
		OrthographicCamera camera = cameraC.camera;

		float mapWidth = cameraC.mapWidth;
		float mapHeight = cameraC.mapHeight;

		/* Make camera follow player */
		float alpha = 0.3f;
//		camera.position.x += Math.round(alpha * (playerCenterX - camera.position.x));
//		camera.position.y += Math.round(alpha * (playerCenterY - camera.position.y));
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
