package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DebugSubSystem implements SubSystem {
	private final AverageVelocity averageVelocity = new AverageVelocity();
	private final List<Vector2> playerPositions = new ArrayList<>();

	@Override
	public void update(Manager manager, float delta) {
		Gdx.graphics.setTitle(Gdx.graphics.getFramesPerSecond() + "");
		String coreUUID = manager.coreUUID;
		ShapeRendererComponent shapeRendererC =
				manager.getComponent(coreUUID, ShapeRendererComponent.class);
		CameraComponent cameraC =
				manager.getComponent(coreUUID, CameraComponent.class);
		BatchComponent batchC =
				manager.getComponent(coreUUID, BatchComponent.class);
		FontComponent fontC =
				manager.getComponent(coreUUID, FontComponent.class);
		String playerUUID = manager.playerUUID;
		BoundingBoxComponent playerBBoxC =
				manager.getComponent(playerUUID, BoundingBoxComponent.class);

		if (!batchC.dispose) {
			fontC.font.getData().setScale(2.0f * cameraC.camera.zoom);


			averageVelocity.update(playerBBoxC.bBox.x, playerBBoxC.bBox.y);

			//debug info
			fontC.text = fontC.text.concat("FPS: " + Gdx.graphics.getFramesPerSecond() + "\n");
			fontC.text = fontC.text.concat("RES: " + Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight() + "\n");
			fontC.text = fontC.text.concat("CAMZOOM: " + cameraC.camera.zoom + "\n");
			fontC.text = fontC.text.concat("AVERAGE VELOCITY: " + averageVelocity.get() + "\n");
			fontC.text = fontC.text.concat("Time to Accel: " + timeToAccel(manager) + "\n");
			fontC.text = fontC.text.concat("Time to Stop: " + timeToStop(manager) + "\n");
			fontC.text = fontC.text.concat("Jump Height: " + jumpHeight(manager) + "\n");

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
//			renderPlayerPath(playerBBoxC.bBox.x, playerBBoxC.bBox.y, shapeRendererC.shapeRenderer);
//			renderBoundingBoxes(manager, shapeRendererC.shapeRenderer);
			shapeRendererC.shapeRenderer.end();
		}
	}

	private float jumpHeight(Manager manager) {
		MovementComponent movementC =
				manager.getComponent(manager.playerUUID, MovementComponent.class);

		float delta = Manager.FIXED_TIMESTEP;
		float jumpHeight = 0.0f;
		float jumpTimer = movementC.jumpTime;
		float jumpForce = movementC.jumpForce;
		float speedY = jumpForce;
		float gravity = movementC.gravity;

		while (speedY > 0.0f) {
			if (jumpTimer > 0.0f) {
				speedY = jumpForce * delta;
				jumpTimer -= delta;
			}

			speedY += gravity * delta * delta;

			if (speedY > 0.0f) jumpHeight += speedY;
		}

		return jumpHeight;
	}

	private float timeToAccel(Manager manager) {
		MovementComponent movementC =
				manager.getComponent(manager.playerUUID, MovementComponent.class);

		float delta = Manager.FIXED_TIMESTEP;
		float timeToAccel = 0.0f;
		float maxSpeed = movementC.maxSpeed * delta;
		float accel = movementC.acceleration;
		float speed = 0.0f;

		while (speed < maxSpeed) {
			speed += accel * delta * delta;
			timeToAccel += delta;
		}

		return timeToAccel;
	}

	private float timeToStop(Manager manager) {
		MovementComponent movementC =
				manager.getComponent(manager.playerUUID, MovementComponent.class);

		float delta = Manager.FIXED_TIMESTEP;
		float timeToStop = 0.0f;
		float speed = movementC.maxSpeed * delta;
		float friction = movementC.friction;

		while (speed > 0.0f) {
			speed -= friction * delta * delta;
			timeToStop += delta;
		}

		return timeToStop;
	}

	private void renderBoundingBoxes(Manager manager, ShapeRenderer shapeRenderer) {
		Set<String> entitiesPossessingBBoxC =
				manager.getAllEntitiesPossessingComponent(BoundingBoxComponent.class);

		for (String entityPossessingBBoxC : entitiesPossessingBBoxC) {
			BoundingBoxComponent bBoxC =
					manager.getComponent(entityPossessingBBoxC, BoundingBoxComponent.class);

			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(bBoxC.bBox.x, bBoxC.bBox.y, bBoxC.bBox.width, bBoxC.bBox.height);
		}
	}

	private void renderPlayerPath(float x, float y, ShapeRenderer shapeRenderer) {
		playerPositions.add(new Vector2(x, y));
		if (playerPositions.size() > 1) {
			Vector2 lastPosition = new Vector2(playerPositions.get(0));
			for (Vector2 position : playerPositions) {
				shapeRenderer.line(lastPosition, position);
				lastPosition.set(position);
			}
		}
	}

	private float getPixelSizeCm(float cameraZoom) {
		return getPixelSizeCm() / cameraZoom;
	}

	private float getPixelSizeCm() {
		return 2.0f / ((Gdx.graphics.getPpcY() + Gdx.graphics.getPpcX()));
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
