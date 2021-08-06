package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.TypeComponent;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.Iterator;
import java.util.Set;

public class ProjectileSubSystem implements SubSystem {
	private final Rectangle activeZone = new Rectangle();

	@Override
	public void update(Manager manager, float delta) {
		String playerUUID = manager.playerUUID;
		BoundingBoxComponent playerBBoxC =
				manager.getComponent(playerUUID, BoundingBoxComponent.class);
		Rectangle playerBBox = playerBBoxC.bBox;

		activeZone.set(
				playerBBox.x + playerBBox.width / 2.0f - Gdx.graphics.getWidth() / 2.0f,
				playerBBox.y + playerBBox.height / 2.0f - Gdx.graphics.getHeight() / 2.0f,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()
		);

		Set<String> entitiesPossessingTypeC =
				manager.getAllEntitiesPossessingComponent(TypeComponent.class);

		// Remove entity from iterator for preventing ConcurrentModificationException
		for (Iterator<String> it = entitiesPossessingTypeC.iterator(); it.hasNext(); ) {
			String entityPossessingTypeC = it.next();
			TypeComponent typeC =
					manager.getComponent(entityPossessingTypeC, TypeComponent.class);
			EntityType entityType = typeC.type;

			if (entityType == EntityType.BULLET) {
				BoundingBoxComponent bulletBBoxC =
						manager.getComponent(entityPossessingTypeC, BoundingBoxComponent.class);
				Rectangle bulletBBox = bulletBBoxC.bBox;

				if (!bulletBBox.overlaps(activeZone)) {
					it.remove();
					manager.killEntity(entityPossessingTypeC);
				}
			}
		}
	}
}
