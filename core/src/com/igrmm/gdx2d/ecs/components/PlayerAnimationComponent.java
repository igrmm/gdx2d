package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.Texture;
import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.enums.TextureAsset;

public class PlayerAnimationComponent implements Component {
	public final Texture texture;

	public PlayerAnimationComponent(Assets assets) {
		texture = assets.getTexture(TextureAsset.PLAYER);
	}
}
