package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.JumpComponent;
import com.igrmm.gdx2d.ecs.components.KeyFrameComponent;
import com.igrmm.gdx2d.ecs.components.PlayerAnimationComponent;
import com.igrmm.gdx2d.ecs.components.VelocityComponent;

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
		VelocityComponent velocityC =
				entityManager.getComponent(playerUUID, VelocityComponent.class);
		JumpComponent jumpC =
				entityManager.getComponent(playerUUID, JumpComponent.class);
		PlayerAnimationComponent playerAnimationC =
				entityManager.getComponent(playerUUID, PlayerAnimationComponent.class);
		KeyFrameComponent keyFrameC =
				entityManager.getComponent(playerUUID, KeyFrameComponent.class);

		Vector2 velocity = velocityC.velocity;
		Vector2 maxVelocity = velocityC.maxVelocity;


		if (right) {
			velocity.x = maxVelocity.x;
			playerAnimationC.setWalkRightAnimation();
		}

		if (left) {
			velocity.x = maxVelocity.x * -1.0f;
			playerAnimationC.setWalkLeftAnimation();
		}

		if (jump) {
			if (jumpC.grounded && !jumpC.jumped) {
				velocity.y += jumpC.jumpVelocity;
				jumpC.jumped = true;
			}
		} else jumpC.jumped = false;

		if (velocity.x == 0 && velocity.y == 0)
			playerAnimationC.setIdleAnimation();

		keyFrameC.setKeyFrame(playerAnimationC.getKeyFrame(delta));
	}
}
