package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.igrmm.gdx2d.ecs.Components;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;

public class RenderingSystem implements System {

	@Override
	public void update(Components components) {
		OrthographicCamera camera = components.graphicsContextComponent.camera;
		float mapWidth = components.graphicsContextComponent.mapWidth;
		float mapHeight = components.graphicsContextComponent.mapHeight;
		OrthogonalTiledMapRenderer mapRenderer = components.graphicsContextComponent.mapRenderer;
		SpriteBatch batch = components.graphicsContextComponent.batch;

		// TEMP PLAYER CODE
		Texture playerTexture = components.animationComponents.get(components.playerID).texture;
		BoundingBoxComponent playerBBox = components.dynamicBoundingBoxComponents.get(components.playerID);
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
