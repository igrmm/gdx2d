package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRendererComponent implements Component {
	public final OrthogonalTiledMapRenderer mapRenderer;

	public MapRendererComponent(OrthogonalTiledMapRenderer mapRenderer) {
		this.mapRenderer = mapRenderer;
	}
}
