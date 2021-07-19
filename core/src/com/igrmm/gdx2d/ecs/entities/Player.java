package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.Saves;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.EntityType;
import com.igrmm.gdx2d.enums.Waypoint;

import java.util.Set;

public class Player {
	public static void spawn(Gdx2D game, EntityManager entityManager) {
		Assets assets = game.assets;
		Saves saves = game.saves;
		String playerUUID = entityManager.playerUUID;

		// Default components
		entityManager.addComponent(playerUUID, new TypeComponent(EntityType.PLAYER));
		AnimationComponent animationC = new AnimationComponent(assets.getAnimationData(AnimationAsset.PLAYER));
		entityManager.addComponent(playerUUID, animationC);
		animationC.offset = 16.0f;
		BoundingBoxComponent playerBBoxC = new BoundingBoxComponent(0.0f, 0.0f, 32.0f, 32.0f);
		entityManager.addComponent(playerUUID, playerBBoxC);
		entityManager.addComponent(playerUUID, new BroadPhaseCollisionComponent());

		// Serializable components
		MovementComponent playerMovC = saves.getMovementComponent();
		entityManager.addComponent(playerUUID, playerMovC);

		if (!saves.isDataLoaded()) {

			// Adjust movement variables
			playerMovC.maxSpeed = 3.0f;
			playerMovC.acceleration = 0.3f;
			playerMovC.friction = 0.2f;
			playerMovC.gravity = -0.5f;
			playerMovC.jumpForce = 10.0f;
		}

		// Make player start at saved waypoint
		Waypoint destinationWaypoint = saves.getWaypointComponent().waypoint;
		Set<String> entitiesPossessingTypeC =
				entityManager.getAllEntitiesPossessingComponent(TypeComponent.class);

		for (String entityPossessingTypeC : entitiesPossessingTypeC) {
			TypeComponent typeC =
					entityManager.getComponent(entityPossessingTypeC, TypeComponent.class);

			if (typeC.type == EntityType.WAYPOINT) {
				WaypointComponent waypointC =
						entityManager.getComponent(entityPossessingTypeC, WaypointComponent.class);

				if (destinationWaypoint == waypointC.waypoint) {
					BoundingBoxComponent waypointBBoxC =
							entityManager.getComponent(entityPossessingTypeC, BoundingBoxComponent.class);
					playerBBoxC.bBox.setPosition(waypointBBoxC.bBox.x, waypointBBoxC.bBox.y);
					break;
				}
			}
		}
	}
}
