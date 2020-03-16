package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.EntityManager;

public interface System {
	public void update(EntityManager manager);
}
