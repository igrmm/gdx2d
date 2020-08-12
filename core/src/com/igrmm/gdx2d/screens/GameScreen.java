package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.igrmm.gdx2d.GameCamera;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.WorldFactory;
import com.igrmm.gdx2d.ecs.World;

public class GameScreen extends AbstractScreen {
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final GameCamera camera;
    private final World world;
    private final Texture playerTex;

    public GameScreen(Gdx2D game) {
        super(game);

        /* CREATE MAP AND SET MAP RENDERER */
        TiledMap map = game.assets.get("maps/start.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        /* SETUP GAME CAMERA */
        float mapWidth = map.getProperties().get("width", Integer.class)
                * map.getProperties().get("tilewidth", Integer.class);
        float mapHeight = map.getProperties().get("height", Integer.class)
                * map.getProperties().get("tileheight", Integer.class);
        camera = new GameCamera();
        camera.setBounds(mapWidth, mapHeight);

        /* SETUP WORLD AND TEMP PLAYER TEX */
        world = WorldFactory.make(map);
        playerTex = game.assets.get("images/player.png");
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void update(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            world.playerBoundingBox.y += 5;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            world.playerBoundingBox.y -= 5;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            world.playerBoundingBox.x -= 5;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            world.playerBoundingBox.x += 5;
        }
    }

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
        batch.draw(playerTex, world.playerBoundingBox.x, world.playerBoundingBox.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        mapRenderer.dispose();
    }
}
