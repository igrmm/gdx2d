package com.igrmm.gdx2d.ecs.components;

import com.igrmm.gdx2d.enums.MapAsset;

public class MapComponent implements Component {
	public final MapAsset mapAsset;

	public MapComponent() {
		this(MapAsset.START);
	}

	public MapComponent(MapAsset mapAsset) {
		this.mapAsset = mapAsset;
	}
}
