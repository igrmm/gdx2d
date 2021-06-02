package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.Disposable;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.CameraComponent;
import com.igrmm.gdx2d.ecs.systems.*;
import com.igrmm.gdx2d.ecs.systems.SubSystem;

import java.util.*;

public class GameScreen extends ScreenAdapter {
	private final EntityManager entityManager;
	private final LinkedHashSet<SubSystem> subSystems;
	private final List<Disposable> disposables;

	public GameScreen(Gdx2D game) {
		entityManager = new EntityManager();
		subSystems = new LinkedHashSet<>();
		disposables = new ArrayList<>();
		subSystems.add(new InitializeSubSystem(game, subSystems, disposables));
	}

	@Override
	public void render(float delta) {
		for (SubSystem subSystem : subSystems) subSystem.update(entityManager, delta);
	}

	@Override
	public void resize(int width, int height) {
		if (entityManager.containsComponent(entityManager.coreUUID, CameraComponent.class)) {
			CameraComponent cameraC =
					entityManager.getComponent(entityManager.coreUUID, CameraComponent.class);
			cameraC.resize(width, height);
		}
	}

	@Override
	public void dispose() {
		for (Disposable disposable : disposables) disposable.dispose();
	}
}
