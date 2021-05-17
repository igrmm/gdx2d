package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.igrmm.gdx2d.ecs.AnimationData;

import java.util.HashMap;
import java.util.Map;

public class AnimationComponent implements Component {
	private final Map<String, Animation<TextureRegion>> animations;
	private String animation = "idle";
	private float stateTime = 0.0f;

	public AnimationComponent(AnimationData animationData, String animation) {
		this(animationData);
		this.animation = animation;
	}

	public AnimationComponent(AnimationData animationData) {
		animations = new HashMap<>();

		for (String animationName : animationData.names) {
			float duration = animationData.durations.get(animationName);
			TextureRegion[] textureRegions = animationData.textureRegions.get(animationName);
			animations.put(animationName, new Animation<>(duration, textureRegions));
		}
	}

	public void setAnimation(String animation) {
		if (!this.animation.equals(animation)) {
			this.animation = animation;
			stateTime = 0.0f;
		}
	}

	public TextureRegion getKeyFrame(float delta) {
		stateTime += delta;
		return animations.get(animation).getKeyFrame(stateTime, true);
	}
}
