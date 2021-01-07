package com.igrmm.gdx2d;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.igrmm.gdx2d.enums.MapAsset;
import com.igrmm.gdx2d.enums.TextureAsset;

public class Assets {
	private final AssetManager assetManager;

	public Assets() {
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	}

	public void load() {
		for (MapAsset mapAsset : MapAsset.values()) {
			assetManager.load(mapAsset.getPath(), TiledMap.class);
		}

		for (TextureAsset textureAsset : TextureAsset.values()) {
			assetManager.load(textureAsset.getPath(), Texture.class);
		}

		assetManager.finishLoading();
	}

	public TiledMap getTiledMap(MapAsset mapAsset) {
		return assetManager.get(mapAsset.getPath());
	}

	public Texture getTexture(TextureAsset textureAsset) {
		return assetManager.get(textureAsset.getPath());
	}

	public void dispose() {
		assetManager.dispose();
	}
}
