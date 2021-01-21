package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.JumpComponent;
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
		VelocityComponent velocityComponent =
				entityManager.getComponent(playerUUID, VelocityComponent.class);
		JumpComponent jumpComponent =
				entityManager.getComponent(playerUUID, JumpComponent.class);
		PlayerAnimationComponent animationComponent =
				entityManager.getComponent(playerUUID, PlayerAnimationComponent.class);

		Vector2 vel = velocityComponent.velocity;
		Vector2 maxVel = velocityComponent.maxVelocity;


		if (right) {
			vel.x = maxVel.x;
			animationComponent.setAnimation(PlayerAnimationComponent.PlayerAnimation.WALK_RIGHT);
		}

		if (left) {
			vel.x = maxVel.x * -1.0f;
			animationComponent.setAnimation(PlayerAnimationComponent.PlayerAnimation.WALK_LEFT);
		}

		if (jump) {
			if (jumpComponent.grounded && !jumpComponent.jumped) {
				vel.y += jumpComponent.jumpVelocity;
				jumpComponent.jumped = true;
			}
		} else jumpComponent.jumped = false;

		if (vel.x == 0 && vel.y == 0)
			animationComponent.setAnimation(PlayerAnimationComponent.PlayerAnimation.IDLE);
	}
}
