package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.Manager;

public interface SubSystem {
	void update(Manager manager, float delta);
}
