package com.igrmm.gdx2d.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.ComponentFactory;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.enums.EntityType;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.ecs.systems.*;
import com.igrmm.gdx2d.ecs.systems.System;
import com.igrmm.gdx2d.enums.MapAsset;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class GameScreen extends ScreenAdapter {
	private final Gdx2D game;
	private final EntityManager entityManager;
	private final LinkedHashSet<System> systems;

	public GameScreen(Gdx2D game) {
		this.game = game;
		entityManager = new EntityManager();
		systems = new LinkedHashSet<>();
	}

	@Override
	public void show() {

		TiledMap tiledMap = game.assets.getTiledMap(MapAsset.START);
		game.mapRenderer.setMap(tiledMap);

		//GENERATE PLAYER ENTITY
		String playerUUID = entityManager.playerUUID;
		entityManager.addComponent(playerUUID, new TypeComponent(EntityType.PLAYER));
		entityManager.addComponent(playerUUID, new PlayerAnimationComponent(game.assets));
		entityManager.addComponent(playerUUID, new InputComponent());
		entityManager.addComponent(playerUUID, new BoundingBoxComponent(100.0f, 100.0f, 32.0f, 32.0f));
		VelocityComponent velocityComponent = new VelocityComponent();
		velocityComponent.maxVelocity.set(4.0f, 4.0f);
		entityManager.addComponent(playerUUID, velocityComponent);
		entityManager.addComponent(playerUUID, new AccelerationComponent());
		entityManager.addComponent(playerUUID, new GravityComponent());
		entityManager.addComponent(playerUUID, new JumpComponent());
		entityManager.addComponent(playerUUID, new BroadPhaseCollisionComponent());

		//GENERATE GRAPHICS ENTITY
		String graphicsUUID = entityManager.graphicsUUID;
		entityManager.addComponent(graphicsUUID, new TypeComponent(EntityType.GRAPHICS));
		entityManager.addComponent(graphicsUUID, new CameraComponent(tiledMap));
		entityManager.addComponent(graphicsUUID, new BatchComponent(game.batch));
		entityManager.addComponent(graphicsUUID, new MapRendererComponent(game.mapRenderer));

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
		java.lang.System.out.println("Number of entities: " + entityManager.entities.size());

		//GENERATE SYSTEMS
		systems.add(new InputSystem());
		systems.add(new PlayerSystem());
		systems.add(new GravitySystem());
		systems.add(new BlockSystem());
		systems.add(new PhysicsSystem());
		systems.add(new RenderingSystem());
	}

	@Override
	public void render(float delta) {
		for (System system : systems)
			system.update(entityManager, delta);
	}
}
