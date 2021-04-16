package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.ecs.systems.*;
import com.igrmm.gdx2d.ecs.systems.SubSystem;

import java.util.*;

public class GameScreen extends ScreenAdapter {
	private final EntityManager entityManager;
	private final LinkedHashSet<SubSystem> subSystems;

	public GameScreen(Gdx2D game) {
		entityManager = new EntityManager();
		subSystems = new LinkedHashSet<>();
		subSystems.add(new InitializeSubSystem(game, subSystems));
	}

	@Override
	public void render(float delta) {
		for (SubSystem subSystem : subSystems)
			subSystem.update(entityManager, delta);
	}

	@Override
	public void dispose() {
		String coreUUID = entityManager.coreUUID;
		BatchComponent batchComponent =
				entityManager.getComponent(coreUUID, BatchComponent.class);
		MapRendererComponent mapRendererComponent =
				entityManager.getComponent(coreUUID, MapRendererComponent.class);
		ShapeRendererComponent shapeRendererComponent =
				entityManager.getComponent(coreUUID, ShapeRendererComponent.class);

		batchComponent.dispose();
		mapRendererComponent.dispose();
		shapeRendererComponent.dispose();
	}
}
