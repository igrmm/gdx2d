package com.igrmm.gdx2d.ecs.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.List;

public class Core {
	public static void spawn(Gdx2D game, Manager manager, List<Disposable> disposables, TiledMap tiledMap) {
		String coreUUID = manager.coreUUID;

		manager.addComponent(coreUUID, new GameComponent(game));
		manager.addComponent(coreUUID, new TypeComponent(EntityType.CORE));
		manager.addComponent(coreUUID, new CameraComponent(tiledMap));
		BatchComponent batchC = new BatchComponent();
		disposables.add(batchC);
		manager.addComponent(coreUUID, batchC);
		MapRendererComponent mapRendererC = new MapRendererComponent(tiledMap);
		disposables.add(mapRendererC);
		manager.addComponent(coreUUID, mapRendererC);
		ShapeRendererComponent shapeRendererC = new ShapeRendererComponent();
		disposables.add(shapeRendererC);
		manager.addComponent(coreUUID, shapeRendererC);
		FontComponent fontC = new FontComponent();
		manager.addComponent(coreUUID, fontC);
		disposables.add(fontC);
		StageComponent stageC = new StageComponent();
		manager.addComponent(coreUUID, stageC);
		disposables.add(stageC);
	}
}
