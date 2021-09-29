package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.Saves;
import com.igrmm.gdx2d.ecs.AnimationData;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.EntityType;
import com.igrmm.gdx2d.enums.Waypoint;

import java.util.Set;

public class Player {
	public static void spawn(Gdx2D game, Manager manager) {
		String playerUUID = manager.playerUUID;
		Assets assets = game.assets;
		Saves saves = game.saves;
		AnimationData animationData = assets.getAnimationData(AnimationAsset.PLAYER);

		// Default components
		manager.addComponent(playerUUID, new TypeComponent(EntityType.PLAYER));
		manager.addComponent(playerUUID, new AnimationComponent(animationData));
		manager.addComponent(playerUUID, new BoundingBoxComponent());
		manager.addComponent(playerUUID, new BroadPhaseCollisionComponent());
		manager.addComponent(playerUUID, new InputComponent());

		// Serializable components
		manager.addComponent(playerUUID, saves.getMovementComponent());

		// Tweak numbers
		AnimationComponent playerAnimationC =
				manager.getComponent(playerUUID, AnimationComponent.class);
		BoundingBoxComponent playerBBoxC =
				manager.getComponent(playerUUID, BoundingBoxComponent.class);
		MovementComponent playerMovC =
				manager.getComponent(playerUUID, MovementComponent.class);

		playerAnimationC.setAnimation("idle_right");
		playerAnimationC.offset = 16.0f;
		playerBBoxC.bBox.width = 32.0f;
		playerBBoxC.bBox.height = 32.0f;

		if (!saves.isDataLoaded()) {

			// Adjust movement variables
			playerMovC.maxSpeed = 240.0f;
			playerMovC.acceleration = 1080.0f;
			playerMovC.friction = 1080.0f;
			playerMovC.gravity = -1800.0f;
			playerMovC.jumpForce = 450.0f;
			playerMovC.jumpTime = 0.2f;
		}

		// Make player start at saved waypoint
		Waypoint destinationWaypoint = saves.getWaypointComponent().waypoint;
		Set<String> entitiesPossessingTypeC =
				manager.getAllEntitiesPossessingComponent(TypeComponent.class);

		for (String entityPossessingTypeC : entitiesPossessingTypeC) {
			TypeComponent typeC =
					manager.getComponent(entityPossessingTypeC, TypeComponent.class);

			if (typeC.type == EntityType.WAYPOINT) {
				WaypointComponent waypointC =
						manager.getComponent(entityPossessingTypeC, WaypointComponent.class);

				if (destinationWaypoint == waypointC.waypoint) {
					BoundingBoxComponent waypointBBoxC =
							manager.getComponent(entityPossessingTypeC, BoundingBoxComponent.class);
					playerBBoxC.bBox.setPosition(waypointBBoxC.bBox.x, waypointBBoxC.bBox.y);
					break;
				}
			}
		}
	}
}
