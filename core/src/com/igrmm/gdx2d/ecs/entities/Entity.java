package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.ecs.EntityManager;

public class Entity {
	public final int id;

	public Entity(EntityManager manager) {
		this.id = manager.getUniqueId();
	}
}
