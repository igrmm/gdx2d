package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;

public class InputSubSystem implements InputProcessor, SubSystem {
	private boolean right = false;
	private boolean left = false;
	private boolean jump = false;

	public InputSubSystem() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.D:
				right = true;
				break;
			case Input.Keys.A:
				left = true;
				break;
			case Input.Keys.SPACE:
				jump = true;
				break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.D:
				right = false;
				break;
			case Input.Keys.A:
				left = false;
				break;
			case Input.Keys.SPACE:
				jump = false;
				break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void update(EntityManager entityManager, float delta) {
		String playerUUID = entityManager.playerUUID;
		VelocityComponent playerVelocityC =
				entityManager.getComponent(playerUUID, VelocityComponent.class);
		JumpComponent playerJumpC =
				entityManager.getComponent(playerUUID, JumpComponent.class);
		AnimationComponent playerAnimationC =
				entityManager.getComponent(playerUUID, AnimationComponent.class);

		Vector2 playerVelocity = playerVelocityC.velocity;
		Vector2 playerMaxVelocity = playerVelocityC.maxVelocity;


		if (right) {
			playerVelocity.x = playerMaxVelocity.x;
			playerAnimationC.setAnimation("walk_right");
		}

		if (left) {
			playerVelocity.x = playerMaxVelocity.x * -1.0f;
			playerAnimationC.setAnimation("walk_left");
		}

		if (jump) {
			if (playerJumpC.grounded && !playerJumpC.jumped) {
				playerVelocity.y += playerJumpC.jumpVelocity;
				playerJumpC.jumped = true;
			}
		} else playerJumpC.jumped = false;

		if (playerVelocity.x == 0 && playerVelocity.y == 0)
			playerAnimationC.setAnimation("idle");
	}
}
