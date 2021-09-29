package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.Disposable;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.CameraComponent;
import com.igrmm.gdx2d.ecs.components.StageComponent;
import com.igrmm.gdx2d.ecs.systems.*;

import java.util.*;

public class GameScreen extends ScreenAdapter {
	private final List<Disposable> disposables;
	private final Manager manager;

	public GameScreen(Gdx2D game) {
		disposables = new ArrayList<>();
		manager = new Manager();
		manager.addVariableTimestepSubSystem(new InitializeSubSystem(game, disposables));
	}

	@Override
	public void render(float delta) {
		manager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		if (manager.containsComponent(manager.coreUUID, CameraComponent.class)) {
			CameraComponent cameraC =
					manager.getComponent(manager.coreUUID, CameraComponent.class);
			cameraC.resize(width, height);
		}
		if (manager.containsComponent(manager.coreUUID, StageComponent.class)) {
			StageComponent stageC =
					manager.getComponent(manager.coreUUID, StageComponent.class);
			stageC.stage.getViewport().update(width, height, true);
		}
	}

	@Override
	public void dispose() {
		for (Disposable disposable : disposables) disposable.dispose();
	}
}
