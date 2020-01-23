package com.igrmm.gdx2d.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.GameObject;

public abstract class MapEntity extends GameObject implements Entity {
	private final String type;
	private final String id;
	private final String name;
	private final boolean collidable;

	public MapEntity(RectangleMapObject rectangleMapObject) {
		super(rectangleMapObject.getRectangle());
		type = rectangleMapObject.getProperties().get("type").toString();
		id = rectangleMapObject.getProperties().get("id").toString();
		name = rectangleMapObject.getName();
		collidable = rectangleMapObject.getProperties().get(
				"collidable",
				false,
				Boolean.class
		);
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isCollidable() {
		return collidable;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}
}
