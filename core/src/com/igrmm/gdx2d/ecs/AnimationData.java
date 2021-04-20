package com.igrmm.gdx2d.ecs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationData {
	public final List<String> names;
	public final Map<String, Float> durations;
	public final Map<String, TextureRegion[]> textureRegions;

	public AnimationData(JsonValue animationJson, Texture animationTexture) {
		names = new ArrayList<>();
		durations = new HashMap<>();
		textureRegions = new HashMap<>();

		String tag = "", jsonTag;
		int width = 0, jsonWidth, height = 0, jsonHeight;
		float duration = 0, jsonDuration;
		for (JsonValue jsonValue : animationJson.get("frames")) {
			jsonTag = jsonValue.name();
			jsonWidth = jsonValue.get("frame").getInt("w");
			jsonHeight = jsonValue.get("frame").getInt("h");
			jsonDuration = jsonValue.getFloat("duration");

			if (!jsonTag.equals(tag)) {
				tag = jsonTag;
				duration = jsonDuration;
				durations.put(tag, duration / 1000.0f);
			} else if (jsonDuration != duration) {
				throw new NullPointerException("frame duration is not regular");
			}

			if (jsonWidth != width) {
				if (width == 0) {
					width = jsonWidth;
				} else {
					throw new NullPointerException("sprite width is not regular");
				}
			}

			if (jsonHeight != height) {
				if (height == 0) {
					height = jsonHeight;
				} else {
					throw new NullPointerException("sprite height is not regular");
				}
			}
		}

		TextureRegion[][] spriteSheet = TextureRegion.split(animationTexture, width, height);

		String animationName;
		int frames;
		TextureRegion[] textureRegion;
		int jsonIndex = 0;
		for (JsonValue jsonValue : animationJson.get("meta").get("frameTags")) {
			animationName = jsonValue.getString("name");
			names.add(animationName);
			frames = jsonValue.getInt("to") - jsonValue.getInt("from") + 1;
			textureRegion = new TextureRegion[frames];
			System.arraycopy(spriteSheet[jsonIndex], 0, textureRegion, 0, frames);
			textureRegions.put(animationName, textureRegion);
			jsonIndex++;
		}
	}
}
