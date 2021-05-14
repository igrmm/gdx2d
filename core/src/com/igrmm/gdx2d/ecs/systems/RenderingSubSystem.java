package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
			camera.update();
			mapRenderer.setView(camera);
			mapRenderer.render();
			batch.setProjectionMatrix(camera.combined);

			/* Render all sprites */
			batch.begin();
			Set<String> entitiesPossessingAnimationC =
					entityManager.getAllEntitiesPossessingComponent(AnimationComponent.class);

			for (String entityPossessingAnimationC : entitiesPossessingAnimationC) {
				float scale = 1.0f;
				if (entityManager.containsComponent(entityPossessingAnimationC, AnimationScaleComponent.class)) {
					AnimationScaleComponent animationScaleC =
							entityManager.getComponent(entityPossessingAnimationC, AnimationScaleComponent.class);
					scale = animationScaleC.scale;
				}

				float offset = 0.0f;
				if (entityManager.containsComponent(entityPossessingAnimationC, AnimationOffsetComponent.class)) {
					AnimationOffsetComponent animationOffsetC =
							entityManager.getComponent(entityPossessingAnimationC, AnimationOffsetComponent.class);
					offset = animationOffsetC.spriteOffset;
				}

				float rotation = 0.0f;
				if (entityManager.containsComponent(entityPossessingAnimationC, AnimationRotationComponent.class)) {
					AnimationRotationComponent animationRotationC =
							entityManager.getComponent(entityPossessingAnimationC, AnimationRotationComponent.class);
					rotation = animationRotationC.rotation;
				}

				BoundingBoxComponent bBoxC =
						entityManager.getComponent(entityPossessingAnimationC, BoundingBoxComponent.class);
				AnimationComponent animationC =
						entityManager.getComponent(entityPossessingAnimationC, AnimationComponent.class);

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
			batch.end();
		}
	}
}
