package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.Saves;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.Set;

public class PortalSubSystem implements SubSystem {
	@Override
	public void update(Manager manager, float delta) {
		Set<String> entitiesPossessingBBoxC =
				manager.getAllEntitiesPossessingComponent(BoundingBoxComponent.class);
		BoundingBoxComponent playerBBoxC =
				manager.getComponent(manager.playerUUID, BoundingBoxComponent.class);

		for (String entityPossessingBBoxC : entitiesPossessingBBoxC) {
			TypeComponent typeC =
					manager.getComponent(entityPossessingBBoxC, TypeComponent.class);
			EntityType entityType = typeC.type;

			if (entityType == EntityType.PORTAL) {
				BoundingBoxComponent portalBBoxC =
						manager.getComponent(entityPossessingBBoxC, BoundingBoxComponent.class);

				Rectangle portalBBox = portalBBoxC.bBox;
				Rectangle playerBBox = playerBBoxC.bBox;

				if (portalBBox.overlaps(playerBBox)) {
					GameComponent gameC =
							manager.getComponent(manager.coreUUID, GameComponent.class);
					Saves saves = gameC.game.saves;
					MapComponent destinationMapC =
							manager.getComponent(entityPossessingBBoxC, MapComponent.class);
					WaypointComponent destinationWaypointC =
							manager.getComponent(entityPossessingBBoxC, WaypointComponent.class);

					saves.setMapComponent(destinationMapC);
					saves.setWaypointComponent(destinationWaypointC);
					gameC.game.setNewGameScreen();
				}
			}
		}
	}
}
