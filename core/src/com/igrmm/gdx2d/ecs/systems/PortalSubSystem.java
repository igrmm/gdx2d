package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.Saves;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.Set;

public class PortalSubSystem implements SubSystem {
	@Override
	public void update(EntityManager entityManager, float delta) {
		Set<String> entitiesPossessingBBoxC =
				entityManager.getAllEntitiesPossessingComponent(BoundingBoxComponent.class);
		BoundingBoxComponent playerBBoxC =
				entityManager.getComponent(entityManager.playerUUID, BoundingBoxComponent.class);

		for (String entityPossessingBBoxC : entitiesPossessingBBoxC) {
			TypeComponent typeC =
					entityManager.getComponent(entityPossessingBBoxC, TypeComponent.class);
			EntityType entityType = typeC.type;

			if (entityType == EntityType.PORTAL) {
				BoundingBoxComponent portalBBoxC =
						entityManager.getComponent(entityPossessingBBoxC, BoundingBoxComponent.class);

				Rectangle portalBBox = portalBBoxC.bBox;
				Rectangle playerBBox = playerBBoxC.bBox;

				if (portalBBox.overlaps(playerBBox)) {
					GameComponent gameC =
							entityManager.getComponent(entityManager.coreUUID, GameComponent.class);
					Saves saves = gameC.game.saves;
					MapComponent destinationMapC =
							entityManager.getComponent(entityPossessingBBoxC, MapComponent.class);
					WaypointComponent destinationWaypointC =
							entityManager.getComponent(entityPossessingBBoxC, WaypointComponent.class);

					saves.setMapComponent(destinationMapC);
					saves.setWaypointComponent(destinationWaypointC);
					gameC.game.setNewGameScreen();
				}
			}
		}
	}
}
