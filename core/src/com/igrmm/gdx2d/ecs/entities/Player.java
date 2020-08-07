package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.ecs.EntityManager;

public class Player extends Entity {
	public Player(EntityManager manager) {
		super(manager);
		manager.entities.add(this);
	}
}
