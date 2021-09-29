package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
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
		StageComponent stageC =
				manager.getComponent(coreUUID, StageComponent.class);

		if (!batchC.dispose) {
			OrthographicCamera camera = cameraC.camera;
			OrthogonalTiledMapRenderer mapRenderer = mapRendererC.mapRenderer;
			SpriteBatch batch = batchC.batch;

			interpolateDrawingPositions(manager);

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			updateCamera(manager, delta);
			mapRenderer.setView(camera);
			mapRenderer.render();
			batch.setProjectionMatrix(camera.combined);

			batch.begin();
			renderSprites(delta, manager, batch);
			batch.end();

			stageC.stage.draw();
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
			animationC.drawingPosition.x = bBoxC.bBox.x * alpha + bBoxC.previousPosition.x * (1.0f - alpha);
			animationC.drawingPosition.y = bBoxC.bBox.y * alpha + bBoxC.previousPosition.y * (1.0f - alpha);
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

	private void updateCamera(Manager manager, float delta) {
		String coreUUID = manager.coreUUID;
		CameraComponent cameraC =
				manager.getComponent(coreUUID, CameraComponent.class);
		OrthographicCamera camera = cameraC.camera;

		float mapWidth = cameraC.mapWidth;
		float mapHeight = cameraC.mapHeight;

		/* Interpolate Camera */
		float alpha = manager.getAlpha();
		camera.position.x = cameraC.position.x * alpha + cameraC.previousPosition.x * (1.0f - alpha);
		camera.position.y = cameraC.position.y * alpha + cameraC.previousPosition.y * (1.0f - alpha);

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

		camera.update();
	}
}
