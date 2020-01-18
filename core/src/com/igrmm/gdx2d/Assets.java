package com.igrmm.gdx2d;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets extends AssetManager {
	public Assets() {
		setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	}
}
