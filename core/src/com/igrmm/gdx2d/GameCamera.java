package com.igrmm.gdx2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class GameCamera extends OrthographicCamera {
	private boolean isBoundsSet = false;
	private float tiledMapWidth, tiledMapHeight;

	public GameCamera() {
		super();
	}

	public void setBounds(float tiledMapWidth, float tiledMapHeight) {
		isBoundsSet = true;
		this.tiledMapWidth = tiledMapWidth;
		this.tiledMapHeight = tiledMapHeight;
	}

	public void lerp(Vector2 target, float alpha) {
		position.x += alpha * (target.x - position.x);
		position.y += alpha * (target.y - position.y);
	}

	@Override
	public void update() {
		if (isBoundsSet) {
			if (tiledMapWidth > viewportWidth) {
				if (position.x < viewportWidth / 2f)
					position.x = viewportWidth / 2f;
				if (position.x > tiledMapWidth - viewportWidth / 2f)
					position.x = tiledMapWidth - viewportWidth / 2f;
			} else {
				position.x = tiledMapWidth / 2f;
			}

			if (tiledMapHeight > viewportHeight) {
				if (position.y < viewportHeight / 2f)
					position.y = viewportHeight / 2f;
				if (position.y > tiledMapHeight - viewportHeight / 2f)
					position.y = tiledMapHeight - viewportHeight / 2f;
			} else {
				position.y = tiledMapHeight / 2f;
			}
		}
		super.update();
	}
}
