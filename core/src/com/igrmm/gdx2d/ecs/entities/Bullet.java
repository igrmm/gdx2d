package com.igrmm.gdx2d.ecs.entities;

import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.EntityType;

public class Bullet {
	public static void spawn(int playerFacingDirection, Manager manager) {
		String coreUUID = manager.coreUUID;
		GameComponent gameC =
				manager.getComponent(coreUUID, GameComponent.class);
		Assets assets = gameC.game.assets;

		String playerUUID = manager.playerUUID;
		BoundingBoxComponent playerBBoxC =
				manager.getComponent(playerUUID, BoundingBoxComponent.class);
		Rectangle playerBBox = playerBBoxC.bBox;

		String bulletUUID = manager.createEntity();
		manager.addComponent(bulletUUID, new TypeComponent(EntityType.BULLET));
		manager.addComponent(bulletUUID, new AnimationComponent(assets.getAnimationData(AnimationAsset.BULLET)));
		manager.addComponent(bulletUUID, new BroadPhaseCollisionComponent());

		// Set up bounding box based on player position
		BoundingBoxComponent bBoxC = new BoundingBoxComponent(
				playerBBox.x + playerBBox.width / 2.0f,
				playerBBox.y + playerBBox.height / 2.0f,
				8.0f,
				8.0f
		);
		manager.addComponent(bulletUUID, bBoxC);

		// Set up movement direction based on players facing direction
		MovementComponent movementC = new MovementComponent();
		movementC.directionInput = playerFacingDirection;
		movementC.maxSpeed = 1000.0f;
		movementC.speed.x = movementC.maxSpeed * playerFacingDirection;
		manager.addComponent(bulletUUID, movementC);
	}
}
