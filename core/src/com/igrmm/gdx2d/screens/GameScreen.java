package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.Components;
import com.igrmm.gdx2d.ecs.Systems;
import com.igrmm.gdx2d.ecs.systems.InputSystem;
import com.igrmm.gdx2d.ecs.systems.PhysicsSystem;
import com.igrmm.gdx2d.ecs.systems.PlayerSystem;
import com.igrmm.gdx2d.ecs.systems.RenderingSystem;

public class GameScreen extends ScreenAdapter {
	private final Gdx2D game;
	private final Components components;
	private final Systems systems;
	private final OrthographicCamera camera;

	public GameScreen(Gdx2D game) {
		this.game = game;
		components = Components.fromAssets(game.assets);
		systems = new Systems();
		systems.add(new InputSystem());
		systems.add(new PlayerSystem());
		systems.add(new PhysicsSystem());
		systems.add(new RenderingSystem());
		camera = components.graphicsContextComponent.camera;
	}

	@Override
	public void render(float delta) {
		systems.update(components);
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
	}

	@Override
	public void dispose() {
		systems.dispose(components);
	}
}
