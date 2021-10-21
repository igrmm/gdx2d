package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.Manager;
import com.igrmm.gdx2d.ecs.components.AnimationComponent;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.InputComponent;
import com.igrmm.gdx2d.ecs.components.TypeComponent;
import com.igrmm.gdx2d.enums.EntityType;

import java.util.Set;

public class ChestSubSystem implements SubSystem {
	@Override
	public void update(Manager manager, float delta) {
		Set<String> entitiesPossessingBBoxC =
				manager.getAllEntitiesPossessingComponent(BoundingBoxComponent.class);
		BoundingBoxComponent playerBBoxC =
				manager.getComponent(manager.playerUUID, BoundingBoxComponent.class);
		InputComponent inputC =
				manager.getComponent(manager.playerUUID, InputComponent.class);

		for (String entityPossessingBBoxC : entitiesPossessingBBoxC) {
			TypeComponent typeC =
					manager.getComponent(entityPossessingBBoxC, TypeComponent.class);
			EntityType entityType = typeC.type;

			if (entityType == EntityType.CHEST) {
				BoundingBoxComponent chestBBoxC =
						manager.getComponent(entityPossessingBBoxC, BoundingBoxComponent.class);
				AnimationComponent chestAnimationC =
						manager.getComponent(entityPossessingBBoxC, AnimationComponent.class);

				Rectangle chestBBox = chestBBoxC.bBox;
				Rectangle playerBBox = playerBBoxC.bBox;

				if (chestBBox.overlaps(playerBBox) && inputC.actionInput)
					chestAnimationC.setAnimation("opened");
			}
		}
	}
}
