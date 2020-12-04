package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.ecs.components.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityManager {
	public final Set<String> entities;
	public final Map<Class<?>, Map<String, ? extends Component>> components;

	public EntityManager() {
		entities = new HashSet<>();
		components = new HashMap<>();
	}
}
