package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.Assets.*;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.ComponentFactory;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.Type;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.ecs.systems.*;
import com.igrmm.gdx2d.ecs.systems.System;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

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

		TiledMap tiledMap = game.assets.getTiledMap(MapAsset.START);

		//GENERATE PLAYER ENTITY
		String playerUUID = entityManager.createEntity();
		entityManager.addComponent(playerUUID, new AnimationComponent(game.assets));
		entityManager.addComponent(playerUUID, new BoundingBoxComponent(100.0f, 100.0f, 32.0f, 32.0f));
		VelocityComponent velocityComponent = new VelocityComponent();
		velocityComponent.maxVelocity.set(10.0f, 10.0f);
		entityManager.addComponent(playerUUID, velocityComponent);
		entityManager.addComponent(playerUUID, new BroadPhaseCollisionComponent());

		//GENERATE GRAPHICS ENTITY
		String graphicsUUID = entityManager.createEntity();
		entityManager.addComponent(graphicsUUID, new TypeComponent(Type.GRAPHICS));
		entityManager.addComponent(graphicsUUID, new GraphicsContextComponent(tiledMap));
		entityManager.addComponent(graphicsUUID, new DisposableComponent());

		//GENERATE ENTITIES AND COMPONENTS FROM TILED MAP (will handle exceptions in the future)
		MapGroupLayer objectsLayer = (MapGroupLayer) tiledMap.getLayers().get("objects");
		for (MapLayer mapLayer : objectsLayer.getLayers()) {
			for (MapObject mapObject : mapLayer.getObjects()) {
				String entityUUID = entityManager.createEntity();
				Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
				entityManager.addComponent(entityUUID, new BoundingBoxComponent(rect));
				MapProperties properties = mapObject.getProperties();
				Iterator<String> iterator = properties.getKeys();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object value = properties.get(key);
					Component component = ComponentFactory.getComponent(key, value);
					if (Objects.nonNull(component))
						entityManager.addComponent(entityUUID, component);
				}
			}
		}

		//GENERATE SYSTEMS
		systems.add(new InputSystem());
		systems.add(new PlayerSystem());
		systems.add(new BlockSystem());
		systems.add(new PhysicsSystem());
		systems.add(new RenderingSystem());
	}

	@Override
	public void render(float delta) {
		for (System system : systems)
			system.update(entityManager);
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
	}

	@Override
	public void dispose() {
		List<GraphicsContextComponent> graphicsContextComponents = entityManager.getAllComponentsOfType(GraphicsContextComponent.class);
		for (GraphicsContextComponent graphicsContextComponent : graphicsContextComponents) {
			graphicsContextComponent.mapRenderer.dispose();
			graphicsContextComponent.batch.dispose();
		}
	}
}
