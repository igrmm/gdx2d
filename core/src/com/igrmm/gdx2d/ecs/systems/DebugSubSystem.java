package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.ShapeRendererComponent;


public class DebugSubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		String coreUUID = entityManager.coreUUID;
		ShapeRendererComponent shapeRendererComponent =
				entityManager.getComponent(coreUUID, ShapeRendererComponent.class);
		ShapeRenderer shapeRenderer = shapeRendererComponent.shapeRenderer;

		String playerUUID = entityManager.playerUUID;
		BoundingBoxComponent playerBBoxC =
				entityManager.getComponent(playerUUID, BoundingBoxComponent.class);

		Rectangle playerBBox = playerBBoxC.bBox;

		shapeRenderer.setColor(Color.RED);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(playerBBox.x, playerBBox.y, playerBBox.width, playerBBox.height);
		shapeRenderer.end();
	}
}
