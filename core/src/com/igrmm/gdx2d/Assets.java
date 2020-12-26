package com.igrmm.gdx2d;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets extends AssetManager {
	public Assets() {
		setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	}

	public void loadMaps() {
		for (MapAsset mapAsset : MapAsset.values()) {
			load(mapAsset.getPath(), TiledMap.class);
		}
	}

	public TiledMap getTiledMap(MapAsset mapAsset) {
		return get(mapAsset.getPath());
	}

	public enum MapAsset {
		START("maps/start.tmx");

		private final String path;

		MapAsset(String path) {
			this.path = path;
		}

		public String getPath() {
			return path;
		}
	}

	public enum TextureAsset {
		PLAYER("images/player.png");

		private final String path;

		TextureAsset(String path) {
			this.path = path;
		}

		public String getPath() {
			return path;
		}
	}
}
