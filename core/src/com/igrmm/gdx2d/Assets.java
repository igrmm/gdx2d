package com.igrmm.gdx2d;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.igrmm.gdx2d.assets.Map;

public class Assets extends AssetManager {
	public Assets() {
		setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	}

	public void loadMaps() {
		for (Map map : Map.values()) {
			load(map.getPath(), TiledMap.class);
		}
	}

	public TiledMap getTiledMap(Map map) {
		return get(map.getPath());
	}
}
