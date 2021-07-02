package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.Saves;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.Set;

public class Player {
	public static void spawn(Gdx2D game, EntityManager entityManager) {
		Assets assets = game.assets;
		Saves saves = game.saves;
		String playerUUID = entityManager.playerUUID;
		entityManager.addComponent(playerUUID, new TypeComponent(EntityType.PLAYER));
		WaypointComponent playerWaypointC = saves.getWaypointComponent();
		entityManager.addComponent(playerUUID, playerWaypointC);
		entityManager.addComponent(playerUUID, saves.getMapComponent());
		entityManager.addComponent(playerUUID, new AnimationComponent(assets.getAnimationData(AnimationAsset.PLAYER)));
		entityManager.addComponent(playerUUID, new AnimationOffsetComponent(16.0f));
		BoundingBoxComponent playerBBoxC = new BoundingBoxComponent(100.0f, 100.0f, 32.0f, 32.0f);
		entityManager.addComponent(playerUUID, playerBBoxC);
		VelocityComponent velocityComponent = new VelocityComponent();
		velocityComponent.maxVelocity.set(4.0f, 4.0f);
		entityManager.addComponent(playerUUID, new DampComponent());
		entityManager.addComponent(playerUUID, velocityComponent);
		entityManager.addComponent(playerUUID, new AccelerationComponent());
		entityManager.addComponent(playerUUID, new GravityComponent());
		entityManager.addComponent(playerUUID, new JumpComponent());
		entityManager.addComponent(playerUUID, new BroadPhaseCollisionComponent());

		// Make player start at saved waypoint
		Set<String> entitiesPossessingTypeC =
				entityManager.getAllEntitiesPossessingComponent(TypeComponent.class);

		for (String entityPossessingTypeC : entitiesPossessingTypeC) {
			TypeComponent typeC =
					entityManager.getComponent(entityPossessingTypeC, TypeComponent.class);

			if (typeC.type == EntityType.WAYPOINT) {
				WaypointComponent waypointC =
						entityManager.getComponent(entityPossessingTypeC, WaypointComponent.class);

				if (playerWaypointC.waypoint == waypointC.waypoint) {
					BoundingBoxComponent waypointBBoxC =
							entityManager.getComponent(entityPossessingTypeC, BoundingBoxComponent.class);
					playerBBoxC.bBox.setPosition(waypointBBoxC.bBox.x, waypointBBoxC.bBox.y);
					break;
				}
			}
		}
	}
}
