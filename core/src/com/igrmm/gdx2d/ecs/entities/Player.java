package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.EntityType;
import com.igrmm.gdx2d.enums.Waypoint;

import java.util.Set;

public class Player {
	public static void spawn(Assets assets, EntityManager entityManager) {
		String playerUUID = entityManager.playerUUID;
		entityManager.addComponent(playerUUID, new TypeComponent(EntityType.PLAYER));
		entityManager.addComponent(playerUUID, new AnimationComponent(assets.getAnimationData(AnimationAsset.PLAYER)));
		entityManager.addComponent(playerUUID, new AnimationOffsetComponent(16.0f));
		BoundingBoxComponent playerBBoxC = new BoundingBoxComponent(100.0f, 100.0f, 32.0f, 32.0f);
		entityManager.addComponent(playerUUID, playerBBoxC);
		VelocityComponent velocityComponent = new VelocityComponent();
		velocityComponent.maxVelocity.set(4.0f, 4.0f);
		entityManager.addComponent(playerUUID, velocityComponent);
		entityManager.addComponent(playerUUID, new AccelerationComponent());
		entityManager.addComponent(playerUUID, new GravityComponent());
		entityManager.addComponent(playerUUID, new JumpComponent());
		entityManager.addComponent(playerUUID, new BroadPhaseCollisionComponent());

		// Make player start at Waypoint.START
		Set<String> entitiesPossessingWaypointC =
				entityManager.getAllEntitiesPossessingComponent(WaypointComponent.class);

		for (String entityPossessingWaypointC : entitiesPossessingWaypointC) {
			WaypointComponent waypointC =
					entityManager.getComponent(entityPossessingWaypointC, WaypointComponent.class);
			if (waypointC.waypoint == Waypoint.START) {
				BoundingBoxComponent waypointBBoxC =
						entityManager.getComponent(entityPossessingWaypointC, BoundingBoxComponent.class);
				playerBBoxC.bBox.setPosition(waypointBBoxC.bBox.x, waypointBBoxC.bBox.y);
				break;
			}
		}
	}
}
