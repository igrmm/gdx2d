package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.enums.TextureAsset;

import java.util.HashMap;
import java.util.Map;

public class PlayerAnimationComponent implements Component {

	public enum PlayerAnimation {IDLE, WALK_RIGHT, WALK_LEFT}

	public static final float SPRITE_OFFSET = 16.0f;

	private final Map<PlayerAnimation, Animation<TextureRegion>> animations;
	private PlayerAnimation playerAnimation;
	private float stateTime = 0.0f;

	public PlayerAnimationComponent(Assets assets) {

		animations = new HashMap<>();
		playerAnimation = PlayerAnimation.IDLE;
		Texture texture = assets.getTexture(TextureAsset.PLAYER);
		TextureRegion[][] spriteSheet = TextureRegion.split(texture, 64, 64);
		TextureRegion[] idle = new TextureRegion[1];
		TextureRegion[] walkRight = new TextureRegion[2];
		TextureRegion[] walkLeft = new TextureRegion[2];

		idle[0] = spriteSheet[2][0];
		for (int i = 0; i < 2; i++) {
			walkRight[i] = spriteSheet[0][i];
			walkLeft[i] = spriteSheet[1][i];
		}

		animations.put(PlayerAnimation.IDLE, new Animation<>(1.0f, idle));
		animations.put(PlayerAnimation.WALK_RIGHT, new Animation<>(0.1f, walkRight));
		animations.put(PlayerAnimation.WALK_LEFT, new Animation<>(0.1f, walkLeft));
	}

	public void setAnimation(PlayerAnimation playerAnimation) {
		if (this.playerAnimation != playerAnimation) {
			this.playerAnimation = playerAnimation;
			stateTime = 0.0f;
		}
	}

	public TextureRegion getKeyFrame(float delta) {
		stateTime += delta;
		return animations.get(playerAnimation).getKeyFrame(stateTime, true);
	}
}
