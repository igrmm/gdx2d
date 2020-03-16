package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.CInput;
import com.igrmm.gdx2d.ecs.components.CPosition;
import com.igrmm.gdx2d.ecs.components.CRigidBody;

public class Player extends Entity {
	public Player(EntityManager manager) {
		super(manager);
		manager.cPositionHashMap.put(this.id, new CPosition());
		manager.cRigidBodyHashMap.put(this.id, new CRigidBody());
		manager.cInputHashMap.put(this.id, new CInput());
		manager.entities.add(this);
	}
}
