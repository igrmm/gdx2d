package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.ecs.components.Component;
import com.igrmm.gdx2d.ecs.systems.SubSystem;

import java.util.*;

public class Manager {
	public final Set<String> entities;
	public final Map<Class<?>, Map<String, ? extends Component>> components;
	public final Set<SubSystem> variableTimestepSubSystems;
	public final Set<SubSystem> fixedTimestepSubSystems;

	private static final float FIXED_TIMESTEP = 1.0f / 60.0f;
	private float accumulator = 0.0f;
	private float alpha = 0.0f;

	//Unique Entities
	public final String playerUUID;
	public final String coreUUID;
	public final String virtualLeftButtonUUID;
	public final String virtualRightButtonUUID;
	public final String virtualAButtonUUID;
	public final String virtualBButtonUUID;

	public Manager() {
		entities = new HashSet<>();
		components = new HashMap<>();
		variableTimestepSubSystems = new LinkedHashSet<>();
		fixedTimestepSubSystems = new LinkedHashSet<>();

		//Unique Entities
		playerUUID = createEntity();
		coreUUID = createEntity();
		virtualLeftButtonUUID = createEntity();
		virtualRightButtonUUID = createEntity();
		virtualAButtonUUID = createEntity();
		virtualBButtonUUID = createEntity();
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

	public <T extends Component> boolean containsComponent(String entity, Class<T> componentType) {
		Map<String, ? extends Component> componentsOfType = components.get(componentType);

		if (componentsOfType == null) return false;

		return componentType.cast(componentsOfType.get(entity)) != null;
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

	public float getAlpha() {
		return alpha;
	}

	public void addVariableTimestepSubSystem(SubSystem subSystem) {
		variableTimestepSubSystems.add(subSystem);
	}

	public void addFixedTimestepSubSystem(SubSystem subSystem) {
		fixedTimestepSubSystems.add(subSystem);
	}

	public void removeSubSystem(SubSystem subSystem) {
		variableTimestepSubSystems.remove(subSystem);
		fixedTimestepSubSystems.remove(subSystem);
	}

	public void update(float delta) {
		// Variable timestep updates
		for (SubSystem variableTimestepSubSystem : variableTimestepSubSystems)
			variableTimestepSubSystem.update(this, delta);

		// Fixed timestep updates
		float frameTime = Math.min(delta, 0.25f);
		accumulator += frameTime;
		while (accumulator >= FIXED_TIMESTEP) {
			for (SubSystem fixedTimestepSubSystem : fixedTimestepSubSystems)
				fixedTimestepSubSystem.update(this, FIXED_TIMESTEP);
			accumulator -= FIXED_TIMESTEP;
		}
		alpha = accumulator / FIXED_TIMESTEP;
	}
}
