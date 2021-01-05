package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.InputComponent;

public class InputSystem implements InputProcessor, System {
	private boolean right = false;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;

	public InputSystem() {
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
			case Input.Keys.W:
				up = true;
				break;
			case Input.Keys.S:
				down = true;
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
			case Input.Keys.W:
				up = false;
				break;
			case Input.Keys.S:
				down = false;
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
	public void update(EntityManager entityManager) {
		InputComponent inputComponent = entityManager.getComponent(entityManager.playerUUID, InputComponent.class);
		inputComponent.right = this.right;
		inputComponent.left = this.left;
		inputComponent.up = this.up;
		inputComponent.down = this.down;
	}
}
