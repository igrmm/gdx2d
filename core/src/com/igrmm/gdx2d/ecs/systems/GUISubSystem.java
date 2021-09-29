package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.InputComponent;
import com.igrmm.gdx2d.ecs.components.MovementComponent;
import com.igrmm.gdx2d.ecs.components.StageComponent;

public class GUISubSystem implements SubSystem {
	private final Stage stage;
	private final InputComponent inputC;

	// on screen controller
	private boolean rightPressed = false;
	private boolean leftPressed = false;

	public GUISubSystem(StageComponent stageC, InputComponent inputC) {
		this.stage = stageC.stage;
		this.inputC = inputC;
		Skin skin = stageC.skin;

		Table table = new Table();
		table.setFillParent(true);
		table.bottom();

		switch (Gdx.app.getType()) {
			case Android:
				addOnScreenController(table, skin);
				break;
			case Desktop:
				addKeyboardController(table);
				break;
			default:
				Gdx.app.exit();
		}

		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
	}

	private void addKeyboardController(Table table) {
		stage.setKeyboardFocus(table);

		table.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == inputC.leftKey) {
					leftPressed = true;
					inputC.directionInput = inputC.directionInput > 0 ? 0 : MovementComponent.LEFT_DIRECTION;
				}

				if (keycode == inputC.rightKey) {
					rightPressed = true;
					inputC.directionInput = inputC.directionInput < 0 ? 0 : MovementComponent.RIGHT_DIRECTION;
				}

				if (keycode == inputC.actionKey)
					inputC.actionInput = true;

				if (keycode == inputC.jumpKey)
					inputC.jumpInput = true;

				return false;
			}

			public boolean keyUp(InputEvent event, int keycode) {
				if (keycode == inputC.leftKey) {
					leftPressed = false;
					inputC.directionInput = rightPressed ? MovementComponent.RIGHT_DIRECTION : 0;
				}

				if (keycode == inputC.rightKey) {
					rightPressed = false;
					inputC.directionInput = leftPressed ? MovementComponent.LEFT_DIRECTION : 0;
				}

				if (keycode == inputC.actionKey)
					inputC.actionInput = false;

				if (keycode == inputC.jumpKey)
					inputC.jumpInput = false;

				return false;
			}
		});
	}

	private void addOnScreenController(Table table, Skin skin) {

		//set button size in centimeters
		float buttonSizeCm = 1.5f;
		float pixelSizeCm = 2.0f / (Gdx.graphics.getPpcX() + Gdx.graphics.getPpcY());
		float buttonSize = buttonSizeCm / pixelSizeCm;

		//set padding
		float buttonPad = 3.0f * Gdx.graphics.getWidth() / 100.0f;

		GameButton leftButton = new GameButton(skin, "left-button");
		table.add(leftButton).width(buttonSize).height(buttonSize).pad(buttonPad);
		leftButton.addListener(new InputListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				leftPressed = true;
				inputC.directionInput = inputC.directionInput > 0 ? 0 : MovementComponent.LEFT_DIRECTION;
				leftButton.over = true;
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				leftPressed = false;
				inputC.directionInput = rightPressed ? MovementComponent.RIGHT_DIRECTION : 0;
				leftButton.over = false;
			}
		});

		GameButton rightButton = new GameButton(skin, "right-button");
		table.add(rightButton).width(buttonSize).height(buttonSize);
		rightButton.addListener(new InputListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				rightPressed = true;
				inputC.directionInput = inputC.directionInput < 0 ? 0 : MovementComponent.RIGHT_DIRECTION;
				rightButton.over = true;
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				rightPressed = false;
				inputC.directionInput = leftPressed ? MovementComponent.LEFT_DIRECTION : 0;
				rightButton.over = false;
			}
		});

		// empty cell
		table.add(new Actor()).expandX();

		GameButton actionButton = new GameButton(skin, "action-button");
		table.add(actionButton).width(buttonSize).height(buttonSize);
		actionButton.addListener(new InputListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				inputC.actionInput = true;
				actionButton.over = true;
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				inputC.actionInput = false;
				actionButton.over = false;
			}
		});

		GameButton jumpButton = new GameButton(skin, "jump-button");
		table.add(jumpButton).width(buttonSize).height(buttonSize).pad(buttonPad);
		jumpButton.addListener(new InputListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				inputC.jumpInput = true;
				jumpButton.over = true;
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				inputC.jumpInput = false;
				jumpButton.over = false;
			}
		});
	}

	@Override
	public void update(Manager manager, float delta) {
		stage.act(delta);
	}

	private static class GameButton extends Button {
		boolean over;

		GameButton(Skin skin, String styleName) {
			super(skin, styleName);
		}

		@Override
		public boolean isOver() {
			return over;
		}
	}
}
