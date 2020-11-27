package com.igrmm.gdx2d.ecs.components;

import com.igrmm.gdx2d.assets.Map;

public class MapComponent implements Component {
	public final Map map;

	public MapComponent(Map map) {
		this.map = map;
	}
}
