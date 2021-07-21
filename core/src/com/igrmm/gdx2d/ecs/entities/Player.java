package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.Saves;
import com.igrmm.gdx2d.ecs.AnimationData;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.EntityType;
import com.igrmm.gdx2d.enums.Waypoint;

import java.util.Set;

public class Player {
	public static void spawn(Gdx2D game, EntityManager entityManager) {
		String playerUUID = entityManager.playerUUID;
		Assets assets = game.assets;
		Saves saves = game.saves;
		AnimationData animationData = assets.getAnimationData(AnimationAsset.PLAYER);

		// Default components
		entityManager.addComponent(playerUUID, new TypeComponent(EntityType.PLAYER));
		entityManager.addComponent(playerUUID, new AnimationComponent(animationData));
		entityManager.addComponent(playerUUID, new BoundingBoxComponent());
		entityManager.addComponent(playerUUID, new BroadPhaseCollisionComponent());

		// Serializable components
		entityManager.addComponent(playerUUID, saves.getMovementComponent());

		// Tweak numbers
		AnimationComponent playerAnimationC =
				entityManager.getComponent(playerUUID, AnimationComponent.class);
		BoundingBoxComponent playerBBoxC =
				entityManager.getComponent(playerUUID, BoundingBoxComponent.class);
		MovementComponent playerMovC =
				entityManager.getComponent(playerUUID, MovementComponent.class);

		playerAnimationC.setAnimation("idle_right");
		playerAnimationC.offset = 16.0f;
		playerBBoxC.bBox.width = 32.0f;
		playerBBoxC.bBox.height = 32.0f;

		if (!saves.isDataLoaded()) {

			// Adjust movement variables
			playerMovC.maxSpeed = 4.0f;
			playerMovC.acceleration = 0.3f;
			playerMovC.friction = 0.3f;
			playerMovC.gravity = -0.5f;
			playerMovC.jumpForce = 7.5f;
			playerMovC.jumpTime = 0.2f;
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
