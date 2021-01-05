package com.igrmm.gdx2d.ecs.components;

import com.igrmm.gdx2d.Assets.MapAsset;

public class MapComponent implements Component {
	public final MapAsset mapAsset;

	public MapComponent(MapAsset mapAsset) {
		this.mapAsset = mapAsset;
	}
}
