package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

public class MapRendererComponent implements Component, Disposable {
	public final OrthogonalTiledMapRenderer mapRenderer;

	public MapRendererComponent(TiledMap tiledMap) {
		this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}

	@Override
	public void dispose() {
		mapRenderer.dispose();
	}
}
