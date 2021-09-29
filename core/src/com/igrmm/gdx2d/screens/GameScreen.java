package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.Disposable;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.Resizable;
import com.igrmm.gdx2d.ecs.systems.*;

public class GameScreen extends ScreenAdapter {
	private final Manager manager;

	public GameScreen(Gdx2D game) {
		manager = new Manager();
		manager.addVariableTimestepSubSystem(new InitializeSubSystem(game));
	}

	@Override
	public void render(float delta) {
		manager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		for (Resizable resizable : manager.resizables) resizable.resize(width, height);
	}

	@Override
	public void dispose() {
		for (Disposable disposable : manager.disposables) disposable.dispose();
	}
}
