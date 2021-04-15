package com.igrmm.gdx2d.ecs.components;

import com.igrmm.gdx2d.Gdx2D;

public class GameComponent implements Component {
	public final Gdx2D game;

	public GameComponent(Gdx2D game) {
		this.game = game;
	}
}
