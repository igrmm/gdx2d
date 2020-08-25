package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.Texture;
import com.igrmm.gdx2d.Assets;

public class AnimationComponent implements Component {
	public final Texture texture;

	public AnimationComponent(Assets assets) {
		texture = assets.get("images/player.png");
	}
}
