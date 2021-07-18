package com.igrmm.gdx2d.ecs.entities;

import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.EntityType;

public class Bullet {
	public static void spawn(int playerFacingDirection, EntityManager entityManager) {
		String coreUUID = entityManager.coreUUID;
		GameComponent gameC =
				entityManager.getComponent(coreUUID, GameComponent.class);
		Assets assets = gameC.game.assets;

		String playerUUID = entityManager.playerUUID;
		BoundingBoxComponent playerBBoxC =
				entityManager.getComponent(playerUUID, BoundingBoxComponent.class);
		Rectangle playerBBox = playerBBoxC.bBox;

		String bulletUUID = entityManager.createEntity();
		entityManager.addComponent(bulletUUID, new TypeComponent(EntityType.BULLET));
		entityManager.addComponent(bulletUUID, new AnimationComponent(assets.getAnimationData(AnimationAsset.BULLET)));
		entityManager.addComponent(bulletUUID, new BroadPhaseCollisionComponent());

		// Set up bounding box based on player position
		BoundingBoxComponent bBoxC = new BoundingBoxComponent(
				playerBBox.x + playerBBox.width / 2.0f,
				playerBBox.y + playerBBox.height / 2.0f,
				8.0f,
				8.0f
		);
		entityManager.addComponent(bulletUUID, bBoxC);

		// Set up movement direction based on players facing direction
		MovementComponent movementC = new MovementComponent();
		movementC.directionInput = playerFacingDirection;
		movementC.maxSpeed = 20.0f;
		movementC.acceleration = movementC.maxSpeed;
		entityManager.addComponent(bulletUUID, movementC);
	}
}
