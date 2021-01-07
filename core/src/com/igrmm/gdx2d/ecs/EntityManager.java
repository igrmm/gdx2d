package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.ecs.components.Component;

import java.util.*;

public class EntityManager {
	public final Set<String> entities;
	public final Map<Class<?>, Map<String, ? extends Component>> components;

	//Unitary Entities
	public final String playerUUID;
	public final String graphicsUUID;

	public EntityManager() {
		entities = new HashSet<>();
		components = new HashMap<>();

		//Unitary Entities
		playerUUID = createEntity();
		graphicsUUID = createEntity();
	}

	public String createEntity() {
		String entity = UUID.randomUUID().toString();
		entities.add(entity);
		return entity;
	}

	public void killEntity(String entity) {
		entities.remove(entity);
		for (Map<String, ? extends Component> component : components.values()) {
			component.remove(entity);
		}
	}

	public <T extends Component> T getComponent(String entity, Class<T> componentType) {
		Map<String, ? extends Component> componentsOfType = components.get(componentType);

		if (componentsOfType == null)
			throw new IllegalArgumentException("GET FAIL: there are no entities with a Component of class: " + componentType);

		T component = componentType.cast(componentsOfType.get(entity));
		if (component == null)
			throw new IllegalArgumentException("GET FAIL: " + entity + " does not possess Component of class\n   missing: " + componentType);

		return component;
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> void addComponent(String entity, T component) {
		Map<String, ? extends Component> componentsOfType = components.get(component.getClass());

		if (componentsOfType == null) {
			componentsOfType = new HashMap<String, T>();
			components.put(component.getClass(), componentsOfType);
		}

		((Map<String, T>) componentsOfType).put(entity, component);
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> List<T> getAllComponentsOfType(Class<T> componentType) {
		Map<String, ? extends Component> componentsOfType = components.get(componentType);

		if (componentsOfType == null) {
			return new LinkedList<>();
		} else {
			return new ArrayList<>((Collection<T>) componentsOfType.values());
		}
	}

	public <T extends Component> Set<String> getAllEntitiesPossessingComponent(Class<T> componentType) {
		Map<String, ? extends Component> componentsOfType = components.get(componentType);

		if (componentsOfType == null)
			return new HashSet<>();

		return componentsOfType.keySet();
	}
}