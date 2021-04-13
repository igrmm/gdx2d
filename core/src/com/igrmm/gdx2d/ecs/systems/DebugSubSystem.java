package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.ShapeRendererComponent;


public class DebugSubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		String graphicsUUID = entityManager.graphicsUUID;
		ShapeRendererComponent shapeRendererComponent =
				entityManager.getComponent(graphicsUUID, ShapeRendererComponent.class);
		ShapeRenderer shapeRenderer = shapeRendererComponent.shapeRenderer;

		String playerUUID = entityManager.playerUUID;
		BoundingBoxComponent bbox =
				entityManager.getComponent(playerUUID, BoundingBoxComponent.class);

		shapeRenderer.setColor(Color.RED);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(bbox.x, bbox.y, bbox.width, bbox.height);
		shapeRenderer.end();
	}
}
