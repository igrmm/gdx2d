package com.igrmm.gdx2d.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.igrmm.gdx2d.DynamicGameObject;

import java.util.ArrayList;

public class Waypoint extends MapEntity {
	public Waypoint(RectangleMapObject rectangleMapObject) {
		super(rectangleMapObject);
	}

	@Override
	public void update(float delta, ArrayList<Entity> queue) {
	}

	@Override
	public void handle(DynamicGameObject dynamicGameObject) {
	}
}
