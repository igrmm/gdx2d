package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GraphicsContextComponent implements Component {
	public final OrthographicCamera camera = new OrthographicCamera();
	public final float mapWidth, mapHeight;
	public final OrthogonalTiledMapRenderer mapRenderer;
	public final SpriteBatch batch = new SpriteBatch();

	public GraphicsContextComponent(TiledMap map) {
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		mapWidth = map.getProperties().get("width", Integer.class)
				* map.getProperties().get("tilewidth", Integer.class);
		mapHeight = map.getProperties().get("height", Integer.class)
				* map.getProperties().get("tileheight", Integer.class);
	}
}
