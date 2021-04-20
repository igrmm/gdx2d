package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.EntityType;

public class Player {
	public static void spawn(Assets assets, EntityManager entityManager) {
		String playerUUID = entityManager.playerUUID;
		entityManager.addComponent(playerUUID, new TypeComponent(EntityType.PLAYER));
		entityManager.addComponent(playerUUID, new AnimationComponent(assets.getAnimationData(AnimationAsset.PLAYER)));
		entityManager.addComponent(playerUUID, new SpriteOffsetComponent(16.0f));
		entityManager.addComponent(playerUUID, new BoundingBoxComponent(100.0f, 100.0f, 32.0f, 32.0f));
		VelocityComponent velocityComponent = new VelocityComponent();
		velocityComponent.maxVelocity.set(4.0f, 4.0f);
		entityManager.addComponent(playerUUID, velocityComponent);
		entityManager.addComponent(playerUUID, new AccelerationComponent());
		entityManager.addComponent(playerUUID, new GravityComponent());
		entityManager.addComponent(playerUUID, new JumpComponent());
		entityManager.addComponent(playerUUID, new BroadPhaseCollisionComponent());
	}
}
