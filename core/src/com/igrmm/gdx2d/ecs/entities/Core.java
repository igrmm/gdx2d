package com.igrmm.gdx2d.ecs.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.List;

public class Core {
	public static void spawn(Gdx2D game, EntityManager entityManager, List<Disposable> disposables, TiledMap tiledMap) {
		String coreUUID = entityManager.coreUUID;

		entityManager.addComponent(coreUUID, new GameComponent(game));
		entityManager.addComponent(coreUUID, new TypeComponent(EntityType.CORE));
		entityManager.addComponent(coreUUID, new CameraComponent(tiledMap));
		BatchComponent batchC = new BatchComponent();
		disposables.add(batchC);
		entityManager.addComponent(coreUUID, batchC);
		MapRendererComponent mapRendererC = new MapRendererComponent(tiledMap);
		disposables.add(mapRendererC);
		entityManager.addComponent(coreUUID, mapRendererC);
		ShapeRendererComponent shapeRendererC = new ShapeRendererComponent();
		disposables.add(shapeRendererC);
		entityManager.addComponent(coreUUID, shapeRendererC);
		FontComponent fontC = new FontComponent();
		entityManager.addComponent(coreUUID, fontC);
		disposables.add(fontC);
	}
}
