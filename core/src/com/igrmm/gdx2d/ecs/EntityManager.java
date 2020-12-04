package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.ecs.components.Component;

import java.util.*;

public class EntityManager {
	public final Set<String> entities;
	public final Map<Class<?>, Map<String, ? extends Component>> components;

	public EntityManager() {
		entities = new HashSet<>();
		components = new HashMap<>();
	}

	public String createEntity() {
		String entity = UUID.randomUUID().toString();
		entities.add(entity);
		return entity;
	}
}
