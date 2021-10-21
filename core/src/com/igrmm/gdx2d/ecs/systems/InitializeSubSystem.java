package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.Saves;
import com.igrmm.gdx2d.ecs.ComponentFactory;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.ecs.entities.Core;
import com.igrmm.gdx2d.ecs.entities.Player;
import com.igrmm.gdx2d.enums.MapAsset;

import java.util.Iterator;

public class InitializeSubSystem implements SubSystem {
	private final Gdx2D game;

	public InitializeSubSystem(Gdx2D game) {
		this.game = game;
	}

	@Override
	public void update(Manager manager, float delta) {

		// Retrieve tiled map using saves
		Assets assets = game.assets;
		Saves saves = game.saves;
		MapAsset mapAsset = saves.getMapComponent().mapAsset;
		TiledMap tiledMap = assets.getTiledMap(mapAsset);

		// Get entities and components from tiled map
		MapGroupLayer objectsLayer = (MapGroupLayer) tiledMap.getLayers().get("objects");
		for (MapLayer mapLayer : objectsLayer.getLayers()) {
			for (MapObject mapObject : mapLayer.getObjects()) {
				String entityUUID = manager.createEntity();
				Rectangle bBox = ((RectangleMapObject) mapObject).getRectangle();
				manager.addComponent(entityUUID, new BoundingBoxComponent(bBox));
				MapProperties properties = mapObject.getProperties();
				Iterator<String> iterator = properties.getKeys();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object value = properties.get(key);
					Component component = ComponentFactory.getComponent(key, value, game.assets);
					if (component != null)
						manager.addComponent(entityUUID, component);
				}
			}
		}

		// Spawn important entities
		Core.spawn(game, manager, tiledMap);
		Player.spawn(game, manager);

		// SubSystem dependencies
		StageComponent stageC =
				manager.getComponent(manager.coreUUID, StageComponent.class);
		InputComponent inputC =
				manager.getComponent(manager.playerUUID, InputComponent.class);

		// Create subsystems
		manager.removeSubSystem(this);
		manager.addVariableTimestepSubSystem(new GUISubSystem(stageC, inputC));
		manager.addVariableTimestepSubSystem(new PlayerSubSystem());
		manager.addVariableTimestepSubSystem(new RenderingSubSystem());
		manager.addFixedTimestepSubSystem(new ProjectileSubSystem());
		manager.addFixedTimestepSubSystem(new PortalSubSystem());
		manager.addFixedTimestepSubSystem(new PhysicsSubSystem());
		manager.addFixedTimestepSubSystem(new BlockSubSystem());
		manager.addFixedTimestepSubSystem(new ChestSubSystem());
		manager.addFixedTimestepSubSystem(new CollisionSubSystem());
		manager.addFixedTimestepSubSystem(new CameraControlSubSystem());
	}
}
