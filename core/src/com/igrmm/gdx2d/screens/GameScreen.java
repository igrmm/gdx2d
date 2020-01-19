package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.igrmm.gdx2d.GameCamera;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.Player;
import com.igrmm.gdx2d.World;
import com.igrmm.gdx2d.entities.Entities;
import com.igrmm.gdx2d.entities.EntityFactory;


public class GameScreen extends AbstractScreen {
	private final Player player;
	private final Texture playerTex;
	private final OrthogonalTiledMapRenderer mapRenderer;
	private final GameCamera camera;
	private final World world;

	public GameScreen(Gdx2D game) {
		super(game);

		player = game.saves.deserializePlayer();
		playerTex = game.assets.get("images/player.png");

		/* CREATE MAP AND SET MAP RENDERER */
		TiledMap map = game.assets.get(player.getCurrentMap());
		mapRenderer = new OrthogonalTiledMapRenderer(map);

		/* SETUP GAME CAMERA */
		float mapWidth = map.getProperties().get("width", Integer.class)
				* map.getProperties().get("tilewidth", Integer.class);
		float mapHeight = map.getProperties().get("height", Integer.class)
				* map.getProperties().get("tileheight", Integer.class);
		camera = new GameCamera();
		camera.setBounds(mapWidth, mapHeight);

		/* SETUP ENTITIES */
		Entities entities = new Entities();
		MapGroupLayer entitiesLayer = (MapGroupLayer) map.getLayers().get("entities");
		for (MapLayer mapLayer : entitiesLayer.getLayers()) {
			for (MapObject mapObject : mapLayer.getObjects()) {
				entities.add(EntityFactory.make(mapObject));
			}
		}

		world = new World(player, entities);
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		player.setZones(width, height);
	}

	@Override
	public void update(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.moveUp();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.moveDown();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.moveLeft();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.moveRight();
		}

		world.update(delta);
		camera.lerp(player.getCenter(), 1f);
	}

	@Override
	public void draw() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		mapRenderer.setView(camera);
		mapRenderer.render();

		batch.begin();
		batch.draw(playerTex, player.getX(), player.getY());
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		mapRenderer.dispose();
	}
}
