package com.igrmm.gdx2d.ecs.systems;

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
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.Component;
import com.igrmm.gdx2d.ecs.entities.Core;
import com.igrmm.gdx2d.ecs.entities.Player;
import com.igrmm.gdx2d.enums.MapAsset;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class InitializeSubSystem implements SubSystem {
	private final Gdx2D game;
	private final LinkedHashSet<SubSystem> subSystems;

	public InitializeSubSystem(Gdx2D game, LinkedHashSet<SubSystem> subSystems) {
		this.game = game;
		this.subSystems = subSystems;
	}

	@Override
	public void update(EntityManager entityManager, float delta) {
		TiledMap tiledMap = game.assets.getTiledMap(MapAsset.START);

		Core.spawn(game, entityManager, tiledMap);
		Player.spawn(game.assets, entityManager);

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

		subSystems.clear();
		subSystems.add(new InputSubSystem());
		subSystems.add(new GravitySubSystem());
		subSystems.add(new BlockSubSystem());
		subSystems.add(new PhysicsSubSystem());
		subSystems.add(new CameraControlSubSystem());
		subSystems.add(new RenderingSubSystem());
		subSystems.add(new DebugSubSystem());
	}
}
