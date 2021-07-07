package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.TypeComponent;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.Iterator;
import java.util.Set;

public class ProjectileSubSystem implements SubSystem {
	private final Rectangle activeZone = new Rectangle();

	@Override
	public void update(EntityManager entityManager, float delta) {
		String playerUUID = entityManager.playerUUID;
		BoundingBoxComponent playerBBoxC =
				entityManager.getComponent(playerUUID, BoundingBoxComponent.class);
		Rectangle playerBBox = playerBBoxC.bBox;

		activeZone.set(
				playerBBox.x + playerBBox.width / 2.0f - Gdx.graphics.getWidth() / 2.0f,
				playerBBox.y + playerBBox.height / 2.0f - Gdx.graphics.getHeight() / 2.0f,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()
		);

		Set<String> entitiesPossessingTypeC =
				entityManager.getAllEntitiesPossessingComponent(TypeComponent.class);

		// Remove entity from iterator for preventing ConcurrentModificationException
		for (Iterator<String> it = entitiesPossessingTypeC.iterator(); it.hasNext(); ) {
			String entityPossessingTypeC = it.next();
			TypeComponent typeC =
					entityManager.getComponent(entityPossessingTypeC, TypeComponent.class);
			EntityType entityType = typeC.type;

			if (entityType == EntityType.BULLET) {
				BoundingBoxComponent bulletBBoxC =
						entityManager.getComponent(entityPossessingTypeC, BoundingBoxComponent.class);
				Rectangle bulletBBox = bulletBBoxC.bBox;

				if (!bulletBBox.overlaps(activeZone)) {
					it.remove();
					entityManager.killEntity(entityPossessingTypeC);
				}
			}
		}
	}
}
