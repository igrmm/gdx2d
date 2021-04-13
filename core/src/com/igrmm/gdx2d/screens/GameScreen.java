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
import com.igrmm.gdx2d.ecs.systems.SubSystem;
import com.igrmm.gdx2d.enums.MapAsset;

import java.util.*;

public class GameScreen extends ScreenAdapter {
	private final Gdx2D game;
	private final EntityManager entityManager;
	private final LinkedHashSet<SubSystem> subSystems;

	public GameScreen(Gdx2D game) {
		this.game = game;
		entityManager = new EntityManager();
		subSystems = new LinkedHashSet<>();
	}

	@Override
	public void show() {

		TiledMap tiledMap = game.assets.getTiledMap(MapAsset.START);

		//GENERATE PLAYER ENTITY
		String playerUUID = entityManager.playerUUID;
		entityManager.addComponent(playerUUID, new TypeComponent(EntityType.PLAYER));
		entityManager.addComponent(playerUUID, new PlayerAnimationComponent(game.assets));
		entityManager.addComponent(playerUUID, new KeyFrameComponent(PlayerAnimationComponent.SPRITE_OFFSET));
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
		BatchComponent batchComponent = new BatchComponent();
		entityManager.addComponent(graphicsUUID, batchComponent);
		MapRendererComponent mapRendererComponent = new MapRendererComponent(tiledMap);
		entityManager.addComponent(graphicsUUID, mapRendererComponent);
		ShapeRendererComponent shapeRendererComponent = new ShapeRendererComponent();
		entityManager.addComponent(graphicsUUID, shapeRendererComponent);

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
					if (component != null)
						entityManager.addComponent(entityUUID, component);
				}
			}
		}
		System.out.println("Number of entities: " + entityManager.entities.size());

		//GENERATE SYSTEMS
		subSystems.add(new InputSubSystem());
		subSystems.add(new GravitySubSystem());
		subSystems.add(new BlockSubSystem());
		subSystems.add(new PhysicsSubSystem());
		subSystems.add(new CameraControlSubSystem());
		subSystems.add(new RenderingSubSystem());
		subSystems.add(new DebugSubSystem());
	}

	@Override
	public void render(float delta) {
		for (SubSystem subSystem : subSystems)
			subSystem.update(entityManager, delta);
	}

	@Override
	public void dispose() {
		String graphicsUUID = entityManager.graphicsUUID;
		BatchComponent batchComponent =
				entityManager.getComponent(graphicsUUID, BatchComponent.class);
		MapRendererComponent mapRendererComponent =
				entityManager.getComponent(graphicsUUID, MapRendererComponent.class);
		ShapeRendererComponent shapeRendererComponent =
				entityManager.getComponent(graphicsUUID, ShapeRendererComponent.class);

		batchComponent.dispose();
		mapRendererComponent.dispose();
		shapeRendererComponent.dispose();
	}
}
