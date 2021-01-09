package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.EntityManager;

public interface System {
	void update(EntityManager entityManager, float delta);
}
