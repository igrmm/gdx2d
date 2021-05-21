package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;


public class InputSubSystem implements InputProcessor, SubSystem {
	private static class TouchInfo {
		public float touchX = 0;
		public float touchY = 0;
		public boolean touched = false;
		public boolean dragged = false;
	}

	private static final int MAX_TOUCHES = 2;
	private final TouchInfo[] touches = new TouchInfo[MAX_TOUCHES];

	private boolean rightKey = false;
	private boolean leftKey = false;
	private boolean aKey = false;
	private boolean bKey = false;

	private boolean rightTouch = false;
	private boolean leftTouch = false;
	private boolean aTouch = false;
	private boolean bTouch = false;
	private boolean zoomInTouch = false;
	private boolean zoomOutTouch = false;

	private final Rectangle rightRectangle = new Rectangle();
	private final Rectangle leftRectangle = new Rectangle();
	private final Rectangle aRectangle = new Rectangle();
	private final Rectangle bRectangle = new Rectangle();
	private final Rectangle zoomInRectangle = new Rectangle();
	private final Rectangle zoomOutRectangle = new Rectangle();

	private int amountScrolled = 0;

	public InputSubSystem() {
		Gdx.input.setInputProcessor(this);
		for (int i = 0; i < MAX_TOUCHES; i++)
			touches[i] = new TouchInfo();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.D:
				rightKey = true;
				break;
			case Input.Keys.A:
				leftKey = true;
				break;
			case Input.Keys.K:
				aKey = true;
				break;
			case Input.Keys.L:
				bKey = true;
				break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.D:
				rightKey = false;
				break;
			case Input.Keys.A:
				leftKey = false;
				break;
			case Input.Keys.K:
				aKey = false;
				break;
			case Input.Keys.L:
				bKey = false;
				break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pointer < MAX_TOUCHES) {
			touches[pointer].touchX = screenX;
			touches[pointer].touchY = screenY;
			touches[pointer].touched = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer < MAX_TOUCHES) {
			touches[pointer].touchX = screenX;
			touches[pointer].touchY = screenY;
			touches[pointer].touched = false;
			touches[pointer].dragged = false;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer < MAX_TOUCHES) {
			touches[pointer].touchX = screenX;
			touches[pointer].touchY = screenY;
			touches[pointer].dragged = true;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		amountScrolled = amount;
		return false;
	}

	@Override
	public void update(EntityManager entityManager, float delta) {
		handleTouches(entityManager);

		CameraComponent cameraC =
				entityManager.getComponent(entityManager.coreUUID, CameraComponent.class);
		if (amountScrolled != 0)
			cameraC.camera.zoom += (0.1f * amountScrolled);
		amountScrolled = 0;

		if (zoomInTouch) {
			cameraC.camera.zoom -= 0.05f;
			zoomInTouch = false;
		}

		if (zoomOutTouch) {
			cameraC.camera.zoom += 0.05f;
			zoomOutTouch = false;
		}

		String playerUUID = entityManager.playerUUID;
		VelocityComponent playerVelocityC =
				entityManager.getComponent(playerUUID, VelocityComponent.class);
		JumpComponent playerJumpC =
				entityManager.getComponent(playerUUID, JumpComponent.class);
		AnimationComponent playerAnimationC =
				entityManager.getComponent(playerUUID, AnimationComponent.class);

		Vector2 playerVelocity = playerVelocityC.velocity;
		Vector2 playerMaxVelocity = playerVelocityC.maxVelocity;

		if (rightKey || rightTouch) {
			playerVelocity.x = playerMaxVelocity.x;
			playerAnimationC.setAnimation("walk_right");
		}

		if (leftKey || leftTouch) {
			playerVelocity.x = playerMaxVelocity.x * -1.0f;
			playerAnimationC.setAnimation("walk_left");
		}

		if (bKey || bTouch) {
			if (playerJumpC.grounded && !playerJumpC.jumped) {
				playerVelocity.y += playerJumpC.jumpVelocity;
				playerJumpC.jumped = true;
			}
		} else playerJumpC.jumped = false;

		if (playerVelocity.x == 0 && playerVelocity.y == 0)
			playerAnimationC.setAnimation("idle");
	}

	private void handleTouches(EntityManager entityManager) {

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();

		/* LEFT BUTTON */
		leftTouch = false;
		UIAnimationComponent vLeftBtnUIAnimationC =
				entityManager.getComponent(entityManager.virtualLeftButtonUUID, UIAnimationComponent.class);
		vLeftBtnUIAnimationC.setAnimation("up");
		BoundingBoxComponent vLeftBtnBBoxC =
				entityManager.getComponent(entityManager.virtualLeftButtonUUID, BoundingBoxComponent.class);
		leftRectangle.set(
				screenWidth / 2.0f - vLeftBtnBBoxC.bBox.x,
				screenHeight / 2.0f - vLeftBtnBBoxC.bBox.y,
				vLeftBtnBBoxC.bBox.width,
				vLeftBtnBBoxC.bBox.height
		);

		/* RIGHT BUTTON */
		rightTouch = false;
		UIAnimationComponent vRightBtnUIAnimationC =
				entityManager.getComponent(entityManager.virtualRightButtonUUID, UIAnimationComponent.class);
		vRightBtnUIAnimationC.setAnimation("up");
		BoundingBoxComponent vRightBtnBBoxC =
				entityManager.getComponent(entityManager.virtualRightButtonUUID, BoundingBoxComponent.class);
		rightRectangle.set(
				screenWidth / 2.0f - vRightBtnBBoxC.bBox.x,
				screenHeight / 2.0f - vRightBtnBBoxC.bBox.y,
				vRightBtnBBoxC.bBox.width,
				vRightBtnBBoxC.bBox.height
		);

		/* A BUTTON */
		aTouch = false;
		UIAnimationComponent vABtnUIAnimationC =
				entityManager.getComponent(entityManager.virtualAButtonUUID, UIAnimationComponent.class);
		vABtnUIAnimationC.setAnimation("up");
		BoundingBoxComponent vABtnBBoxC =
				entityManager.getComponent(entityManager.virtualAButtonUUID, BoundingBoxComponent.class);
		aRectangle.set(
				screenWidth / 2.0f - vABtnBBoxC.bBox.x,
				screenHeight / 2.0f - vABtnBBoxC.bBox.y,
				vABtnBBoxC.bBox.width,
				vABtnBBoxC.bBox.height
		);

		/* B BUTTON */
		bTouch = false;
		UIAnimationComponent vBBtnUIAnimationC =
				entityManager.getComponent(entityManager.virtualBButtonUUID, UIAnimationComponent.class);
		vBBtnUIAnimationC.setAnimation("up");
		BoundingBoxComponent vBBtnBBoxC =
				entityManager.getComponent(entityManager.virtualBButtonUUID, BoundingBoxComponent.class);
		bRectangle.set(
				screenWidth / 2.0f - vBBtnBBoxC.bBox.x,
				screenHeight / 2.0f - vBBtnBBoxC.bBox.y,
				vBBtnBBoxC.bBox.width,
				vBBtnBBoxC.bBox.height
		);

		/* ZOOM IN BUTTON */
		zoomInRectangle.set(
				screenWidth - 200.0f,
				screenHeight - 200.0f,
				200.0f,
				200.0f
		);

		/* ZOOM OUT BUTTON */
		zoomOutRectangle.set(
				0.0f,
				screenHeight - 200.0f,
				200.0f,
				200.0f
		);

		for (int i = 0; i < MAX_TOUCHES; i++) {
			if (touches[i].touched) {
				if (leftRectangle.contains(touches[i].touchX, (screenHeight - touches[i].touchY))) {
					vLeftBtnUIAnimationC.setAnimation("down");
					leftTouch = true;
				}

				if (rightRectangle.contains(touches[i].touchX, (screenHeight - touches[i].touchY))) {
					vRightBtnUIAnimationC.setAnimation("down");
					rightTouch = true;
				}

				if (aRectangle.contains(touches[i].touchX, (screenHeight - touches[i].touchY))) {
					vABtnUIAnimationC.setAnimation("down");
					aTouch = true;
				}

				if (bRectangle.contains(touches[i].touchX, (screenHeight - touches[i].touchY))) {
					vBBtnUIAnimationC.setAnimation("down");
					bTouch = true;
				}

				if (!touches[i].dragged) {
					if (zoomInRectangle.contains(touches[i].touchX, (screenHeight - touches[i].touchY))) {
						zoomInTouch = true;
						touches[i].touched = false;
					}

					if (zoomOutRectangle.contains(touches[i].touchX, (screenHeight - touches[i].touchY))) {
						zoomOutTouch = true;
						touches[i].touched = false;
					}
				}
			}
		}
	}
}
