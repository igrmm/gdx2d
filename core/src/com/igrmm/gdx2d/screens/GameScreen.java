package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.InputComponent;

public class GameScreen extends ScreenAdapter {
    private final Gdx2D game;
    private final EntityManager entityManager;
    private final InputComponent inputs;
    private final OrthographicCamera camera;

    public GameScreen(Gdx2D game) {
        this.game = game;
        entityManager = new EntityManager(game.assets);
        inputs = entityManager.playerInputComponent;
        camera = entityManager.graphicsContextComponent.camera;
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            inputs.up = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            inputs.down = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            inputs.left = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            inputs.right = true;
        }

        entityManager.update(delta);
    }

    @Override
    public void dispose() {
        entityManager.dispose();
    }
}
