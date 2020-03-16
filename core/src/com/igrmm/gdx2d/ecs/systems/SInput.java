package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.entities.Entity;

public class SInput implements System {
	@Override
	public void update(EntityManager manager) {
		for (Entity entity : manager.entities) {
			manager.cInputHashMap.get(entity.id).moveDownPressed = false;
			manager.cInputHashMap.get(entity.id).moveUpPressed = false;
			manager.cInputHashMap.get(entity.id).moveLeftPressed = false;
			manager.cInputHashMap.get(entity.id).moveRightPressed = false;

			if (Gdx.input.isKeyPressed(manager.cInputHashMap.get(entity.id).moveDownKey)) {
				manager.cInputHashMap.get(entity.id).moveDownPressed = true;
			}

			if (Gdx.input.isKeyPressed(manager.cInputHashMap.get(entity.id).moveUpKey)) {
				manager.cInputHashMap.get(entity.id).moveUpPressed = true;
			}

			if (Gdx.input.isKeyPressed(manager.cInputHashMap.get(entity.id).moveLeftKey)) {
				manager.cInputHashMap.get(entity.id).moveLeftPressed = true;
			}

			if (Gdx.input.isKeyPressed(manager.cInputHashMap.get(entity.id).moveRightKey)) {
				manager.cInputHashMap.get(entity.id).moveRightPressed = true;
			}
		}
	}
}
