package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.PlayerAnimationComponent;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.GraphicsContextComponent;


public class RenderingSystem implements System {

	@Override
	public void update(EntityManager entityManager, float delta) {
		String graphicsUUID = entityManager.graphicsUUID;
		GraphicsContextComponent GCComponent = entityManager.getComponent(graphicsUUID, GraphicsContextComponent.class);

		OrthographicCamera camera = GCComponent.camera;
		float mapWidth = GCComponent.mapWidth;
		float mapHeight = GCComponent.mapHeight;
		OrthogonalTiledMapRenderer mapRenderer = GCComponent.mapRenderer;
		SpriteBatch batch = GCComponent.batch;

		// TEMP PLAYER CODE
		String playerUUID = entityManager.playerUUID;
		PlayerAnimationComponent playerAnimationComponent = entityManager.getComponent(playerUUID, PlayerAnimationComponent.class);
		Texture playerTexture = playerAnimationComponent.texture;
		BoundingBoxComponent playerBBox = entityManager.getComponent(playerUUID, BoundingBoxComponent.class);
		float playerX = playerBBox.x;
		float playerY = playerBBox.y;
		float playerCenterX = playerX + playerBBox.width / 2.0f;
		float playerCenterY = playerY + playerBBox.height / 2.0f;

		/* Make camera follow player */
		float alpha = 0.3f;
		camera.position.x += alpha * (playerCenterX - camera.position.x);
		camera.position.y += alpha * (playerCenterY - camera.position.y);

		/* Limit camera position inside tiled map bounds */
		float minCameraPositionX = camera.viewportWidth / 2.0f;
		float minCameraPositionY = camera.viewportHeight / 2.0f;
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

		/* Render map and player */
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		mapRenderer.setView(camera);
		mapRenderer.render();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(playerTexture, playerX, playerY);
		batch.end();
	}
}
