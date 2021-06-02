package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class CameraComponent implements Component {
	public final OrthographicCamera camera = new OrthographicCamera();
	public final float mapWidth, mapHeight;

	public CameraComponent(TiledMap tiledMap) {
		float tileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
		float tileHeight = tiledMap.getProperties().get("tileheight", Integer.class);
		float mapWidthInTiles = tiledMap.getProperties().get("width", Integer.class);
		float mapHeightInTiles = tiledMap.getProperties().get("height", Integer.class);

		if (tileWidth != tileHeight) throw new IllegalStateException("Tile width and Tile height are not equal.");

		mapWidth = mapWidthInTiles * tileWidth;
		mapHeight = mapHeightInTiles * tileHeight;

		camera.setToOrtho(false);

		/* DPI STUFF - target on mobile: 1 tile (32px) ~ 40mm */
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			float TILE_SIZE_PX = tileWidth;
			float TILE_SIZE_CM = 0.4f;
			float PIXEL_P_CM = (Gdx.graphics.getPpcX() + Gdx.graphics.getPpcY()) / 2.0f;
			camera.zoom = 1.0f / (TILE_SIZE_CM / (TILE_SIZE_PX / PIXEL_P_CM));
		}
	}

	public void resize(int width, int height) {
		camera.setToOrtho(false);
	}
}
