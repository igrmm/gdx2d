package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;


public class PlayerSubSystem implements InputProcessor, SubSystem {
	private static class TouchInfo {
		public float touchX = 0;
		public float touchY = 0;
		public boolean touched = false;
		public boolean dragged = false;
	}

	private static final int MAX_TOUCHES = 2;
	private final TouchInfo[] touches = new TouchInfo[MAX_TOUCHES];

	private boolean rightKeyDown = false;
	private boolean leftKeyDown = false;
	private boolean actionKeyDown = false;
	private boolean jumpKeyDown = false;

	private boolean rightTouch = false;
	private boolean leftTouch = false;
	private boolean actionTouch = false;
	private boolean jumpTouch = false;
	private boolean zoomInTouch = false;
	private boolean zoomOutTouch = false;

	private final Rectangle rightRectangle = new Rectangle();
	private final Rectangle leftRectangle = new Rectangle();
	private final Rectangle aRectangle = new Rectangle();
	private final Rectangle bRectangle = new Rectangle();
	private final Rectangle zoomInRectangle = new Rectangle();
	private final Rectangle zoomOutRectangle = new Rectangle();

	private int amountScrolled = 0;

	private int facing = MovementComponent.RIGHT_DIRECTION;

	private int movementInput = 0;

	public PlayerSubSystem() {
		Gdx.input.setInputProcessor(this);
		for (int i = 0; i < MAX_TOUCHES; i++)
			touches[i] = new TouchInfo();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.D:
				rightKeyDown = true;
				movementInput = movementInput < 0 ? 0 : MovementComponent.RIGHT_DIRECTION;
				break;
			case Input.Keys.A:
				leftKeyDown = true;
				movementInput = movementInput > 0 ? 0 : MovementComponent.LEFT_DIRECTION;
				break;
			case Input.Keys.K:
				actionKeyDown = true;
				break;
			case Input.Keys.L:
				jumpKeyDown = true;
				break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.D:
				rightKeyDown = false;
				movementInput = leftKeyDown ? MovementComponent.LEFT_DIRECTION : 0;
				break;
			case Input.Keys.A:
				leftKeyDown = false;
				movementInput = rightKeyDown ? MovementComponent.RIGHT_DIRECTION : 0;
				break;
			case Input.Keys.K:
				actionKeyDown = false;
				break;
			case Input.Keys.L:
				jumpKeyDown = false;
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
		if (Gdx.app.getType() == Application.ApplicationType.Android)
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
		MovementComponent playerMovC =
				entityManager.getComponent(playerUUID, MovementComponent.class);
		AnimationComponent playerAnimationC =
				entityManager.getComponent(playerUUID, AnimationComponent.class);

		playerMovC.direction = movementInput;

		playerMovC.jumped = jumpKeyDown || jumpTouch;

		if (movementInput != 0) facing = movementInput;

		/* ANIMATIONS */
		if (facing == MovementComponent.RIGHT_DIRECTION) {
			if (playerMovC.speed.x != 0.0f)
				playerAnimationC.setAnimation("walk_right");
			else
				playerAnimationC.setAnimation("idle_right");
		}

		if (facing == MovementComponent.LEFT_DIRECTION) {
			if (playerMovC.speed.x != 0.0f)
				playerAnimationC.setAnimation("walk_left");
			else
				playerAnimationC.setAnimation("idle_left");
		}
	}

	private void handleTouches(EntityManager entityManager) {

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();

		/* LEFT BUTTON */
		leftTouch = false;
		movementInput = rightTouch ? MovementComponent.RIGHT_DIRECTION : 0;
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
		movementInput = leftTouch ? MovementComponent.LEFT_DIRECTION : 0;
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
		actionTouch = false;
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
		jumpTouch = false;
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
					movementInput = movementInput > 0 ? 0 : MovementComponent.LEFT_DIRECTION;
				}

				if (rightRectangle.contains(touches[i].touchX, (screenHeight - touches[i].touchY))) {
					vRightBtnUIAnimationC.setAnimation("down");
					rightTouch = true;
					movementInput = movementInput < 0 ? 0 : MovementComponent.RIGHT_DIRECTION;
				}

				if (aRectangle.contains(touches[i].touchX, (screenHeight - touches[i].touchY))) {
					vABtnUIAnimationC.setAnimation("down");
					actionTouch = true;
				}

				if (bRectangle.contains(touches[i].touchX, (screenHeight - touches[i].touchY))) {
					vBBtnUIAnimationC.setAnimation("down");
					jumpTouch = true;
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
