package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class CameraComponent implements Component {
	public final OrthographicCamera camera = new OrthographicCamera();
	public final float mapWidth, mapHeight;

	public CameraComponent(TiledMap tiledMap) {
		mapWidth = tiledMap.getProperties().get("width", Integer.class)
				* tiledMap.getProperties().get("tilewidth", Integer.class);
		mapHeight = tiledMap.getProperties().get("height", Integer.class)
				* tiledMap.getProperties().get("tileheight", Integer.class);

		camera.setToOrtho(false);
	}
}
