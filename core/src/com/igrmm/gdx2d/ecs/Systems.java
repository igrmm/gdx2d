package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.ecs.systems.System;

import java.util.LinkedHashSet;

public class Systems implements System {
	private final LinkedHashSet<System> systems;

	public Systems() {
		systems = new LinkedHashSet<>();
	}

	public void add(System system) {
		systems.add(system);
	}

	@Override
	public void update(Components components) {
		for (System system : systems) {
			system.update(components);
		}
	}

	public void dispose(Components components) {
		components.graphicsContextComponent.batch.dispose();
		components.graphicsContextComponent.mapRenderer.dispose();
	}
}
