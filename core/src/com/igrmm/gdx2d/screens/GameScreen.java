package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.igrmm.gdx2d.GameCamera;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.World;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.entities.Player;

public class GameScreen extends AbstractScreen {
	private final Texture playerTex;
	private final Player player;
	private final EntityManager entityManager;
	private final OrthogonalTiledMapRenderer mapRenderer;
	private final GameCamera camera;
	private final World world;

	public GameScreen(Gdx2D game) {
		super(game);

		playerTex = game.assets.get("images/player.png");

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

		entityManager = new EntityManager();
		player = new Player(entityManager);

		world = new World();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
	}

	@Override
	public void update(float delta) {

		world.update(delta);
//		camera.lerp(player.getCenter(), 1f);
	}

	@Override
	public void draw() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		mapRenderer.setView(camera);
		mapRenderer.render();

		batch.begin();
		batch.draw(
				playerTex,
				entityManager.cPositionHashMap.get(player.id).x,
				entityManager.cPositionHashMap.get(player.id).y
		);
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		mapRenderer.dispose();
	}
}
