package com.igrmm.gdx2d.ecs.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.EntityType;

public class Core {
	public static void spawn(Gdx2D game, EntityManager entityManager, TiledMap tiledMap) {
		String coreUUID = entityManager.coreUUID;

		entityManager.addComponent(coreUUID, new GameComponent(game));
		entityManager.addComponent(coreUUID, new TypeComponent(EntityType.CORE));
		entityManager.addComponent(coreUUID, new CameraComponent(tiledMap));
		entityManager.addComponent(coreUUID, new BatchComponent());
		entityManager.addComponent(coreUUID, new MapRendererComponent(tiledMap));
		entityManager.addComponent(coreUUID, new ShapeRendererComponent());
	}
}
