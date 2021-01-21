package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.EntityManager;

public interface SubSystem {
	void update(EntityManager entityManager, float delta);
}
