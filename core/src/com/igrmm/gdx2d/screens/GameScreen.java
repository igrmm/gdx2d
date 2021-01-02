package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.Components;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.systems.*;
import com.igrmm.gdx2d.ecs.systems.System;

import java.util.LinkedHashSet;

public class GameScreen extends ScreenAdapter {
	private final Gdx2D game;
	private final EntityManager entityManager;
	private final LinkedHashSet<System> systems;
	private final Components components;
	private final OrthographicCamera camera;


	public GameScreen(Gdx2D game) {
		this.game = game;
		entityManager = new EntityManager();
		systems = new LinkedHashSet<>();
		components = Components.fromAssets(game.assets);
		camera = components.graphicsContextComponent.camera;
	}

	@Override
	public void show() {
		systems.add(new InputSystem());
		systems.add(new PlayerSystem());
		systems.add(new BlockSystem());
		systems.add(new PhysicsSystem());
		systems.add(new RenderingSystem());
	}

	@Override
	public void render(float delta) {
		for (System system : systems)
			system.update(components);
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
	}

	@Override
	public void dispose() {
		components.graphicsContextComponent.batch.dispose();
		components.graphicsContextComponent.mapRenderer.dispose();
	}
}
