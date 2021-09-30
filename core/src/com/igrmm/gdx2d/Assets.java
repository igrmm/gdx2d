package com.igrmm.gdx2d;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonValue;
import com.igrmm.gdx2d.ecs.AnimationData;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.MapAsset;
import com.igrmm.gdx2d.enums.TextureAsset;

import java.util.HashMap;
import java.util.Map;

public class Assets {
	private final AssetManager assetManager;
	private final Map<AnimationAsset, AnimationData> animationData;

	public Assets() {
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.setLoader(JsonValue.class, new JsonLoader(new InternalFileHandleResolver()));
		animationData = new HashMap<>();
	}

	public void load() {
		for (MapAsset mapAsset : MapAsset.values()) {
			assetManager.load(mapAsset.getPath(), TiledMap.class);
		}

		for (TextureAsset textureAsset : TextureAsset.values()) {
			assetManager.load(textureAsset.getPath(), Texture.class);
		}

		for (AnimationAsset animationAsset : AnimationAsset.values()) {
			assetManager.load(animationAsset.getPath(), JsonValue.class);
		}

		assetManager.load("ui/uiskin.json", Skin.class);

		assetManager.finishLoading();

		for (AnimationAsset animationAsset : AnimationAsset.values()) {
			JsonValue animationJson = assetManager.get(animationAsset.getPath());
			String texturePath = "images/" + animationJson.get("meta").getString("image");
			if (TextureAsset.contains(texturePath)) {
				Texture animationTexture = assetManager.get(texturePath, Texture.class);
				animationData.put(animationAsset, new AnimationData(animationJson, animationTexture));
			} else throw new NullPointerException("wrong texture path: " + texturePath);
		}
	}

	public TiledMap getTiledMap(MapAsset mapAsset) {
		return assetManager.get(mapAsset.getPath());
	}

	public AnimationData getAnimationData(AnimationAsset animationAsset) {
		return animationData.get(animationAsset);
	}

	public Skin getUISkin() {
		return assetManager.get("ui/uiskin.json");
	}

	public void dispose() {
		assetManager.dispose();
	}
}
