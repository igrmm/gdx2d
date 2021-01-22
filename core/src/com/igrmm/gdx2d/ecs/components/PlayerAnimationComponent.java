package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.enums.TextureAsset;

import java.util.HashMap;
import java.util.Map;

public class PlayerAnimationComponent implements Component {
	public static final float SPRITE_OFFSET = 16.0f;

	private static final int SPRITE_SIZE = 64;
	private static final int WALK_RIGHT = 0;
	private static final int WALK_RIGHT_FRAMES = 2;
	private static final int WALK_LEFT = 1;
	private static final int WALK_LEFT_FRAMES = 2;
	private static final int IDLE = 2;
	private static final int IDLE_FRAMES = 1;

	private int animation = IDLE;

	private float stateTime = 0.0f;

	private final Map<Integer, Animation<TextureRegion>> animations;

	public PlayerAnimationComponent(Assets assets) {
		animations = new HashMap<>();
		Texture texture = assets.getTexture(TextureAsset.PLAYER);
		TextureRegion[][] spriteSheet = TextureRegion.split(texture, SPRITE_SIZE, SPRITE_SIZE);
		TextureRegion[] idle = new TextureRegion[IDLE_FRAMES];
		TextureRegion[] walkRight = new TextureRegion[WALK_RIGHT_FRAMES];
		TextureRegion[] walkLeft = new TextureRegion[WALK_LEFT_FRAMES];

		int maxFrames = texture.getWidth() / SPRITE_SIZE;
		for (int i = 0; i < maxFrames; i++) {
			if (i < WALK_RIGHT_FRAMES)
				walkRight[i] = spriteSheet[WALK_RIGHT][i];

			if (i < WALK_LEFT_FRAMES)
				walkLeft[i] = spriteSheet[WALK_LEFT][i];

			if (i < IDLE_FRAMES)
				idle[i] = spriteSheet[IDLE][i];
		}

		animations.put(IDLE, new Animation<>(1.0f, idle));
		animations.put(WALK_RIGHT, new Animation<>(0.1f, walkRight));
		animations.put(WALK_LEFT, new Animation<>(0.1f, walkLeft));
	}

	public void setWalkRightAnimation() {
		if (animation != WALK_RIGHT) {
			animation = WALK_RIGHT;
			stateTime = 0.0f;
		}
	}

	public void setWalkLeftAnimation() {
		if (animation != WALK_LEFT) {
			animation = WALK_LEFT;
			stateTime = 0.0f;
		}
	}

	public void setIdleAnimation() {
		if (animation != IDLE) {
			animation = IDLE;
			stateTime = 0.0f;
		}
	}

	public TextureRegion getKeyFrame(float delta) {
		stateTime += delta;
		return animations.get(animation).getKeyFrame(stateTime, true);
	}
}
