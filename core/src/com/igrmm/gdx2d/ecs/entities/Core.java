package com.igrmm.gdx2d.ecs.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.EntityType;

public class Core {
	public static void spawn(Gdx2D game, Manager manager, TiledMap tiledMap) {
		String coreUUID = manager.coreUUID;

		manager.addComponent(coreUUID, new GameComponent(game));
		manager.addComponent(coreUUID, new TypeComponent(EntityType.CORE));
		manager.addComponent(coreUUID, new CameraComponent(tiledMap));
		BatchComponent batchC = new BatchComponent();
		manager.disposables.add(batchC);
		manager.addComponent(coreUUID, batchC);
		MapRendererComponent mapRendererC = new MapRendererComponent(tiledMap);
		manager.disposables.add(mapRendererC);
		manager.addComponent(coreUUID, mapRendererC);
		ShapeRendererComponent shapeRendererC = new ShapeRendererComponent();
		manager.disposables.add(shapeRendererC);
		manager.addComponent(coreUUID, shapeRendererC);
		FontComponent fontC = new FontComponent();
		manager.addComponent(coreUUID, fontC);
		manager.disposables.add(fontC);
		StageComponent stageC = new StageComponent();
		manager.addComponent(coreUUID, stageC);
		manager.disposables.add(stageC);
	}
}
