package com.igrmm.gdx2d.ecs;

import java.util.HashSet;
import java.util.Set;

public class EntityManager {
	public final Set<String> entities;

	public EntityManager() {
		entities = new HashSet<>();
	}
}
