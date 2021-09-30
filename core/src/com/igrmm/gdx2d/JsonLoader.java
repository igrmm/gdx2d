package com.igrmm.gdx2d;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class JsonLoader extends AsynchronousAssetLoader<JsonValue, JsonLoader.JsonParameter> {
	private JsonValue jsonValue;

	public JsonLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, JsonParameter parameter) {
		jsonValue = null;
		jsonValue = new JsonReader().parse(file);
	}

	@Override
	public JsonValue loadSync(AssetManager manager, String fileName, FileHandle file, JsonParameter parameter) {
		JsonValue jsonValue = this.jsonValue;
		this.jsonValue = null;
		return jsonValue;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, JsonParameter parameter) {
		return null;
	}

	public static class JsonParameter extends AssetLoaderParameters<JsonValue> {
	}
}
