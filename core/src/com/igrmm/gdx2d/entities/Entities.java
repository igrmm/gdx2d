package com.igrmm.gdx2d.entities;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Iterator;

public class Entities implements Iterable<Entity> {
	private final ArrayList<Entity> entities;

	public Entities() {
		entities = new ArrayList<>();
	}

	public void add(Entity e) {
		entities.add(e);
	}

	public Entity get(String name) {
		for (Entity e : entities) {
			if (e.rectangleMapObject.getName().equals(name)) return e;
		}
		Gdx.app.error("ERROR", "Entity not found!");
		return null;
	}

	public Entity get(int id) {
		for (Entity e : entities) {
			if (e.rectangleMapObject.getProperties().get("id").equals(id)) return e;
		}
		Gdx.app.error("ERROR", "Entity not found!");
		return null;
	}

	public void clear() {
		entities.clear();
	}

	@Override
	public Iterator<Entity> iterator() {
		return entities.iterator();
	}
}
