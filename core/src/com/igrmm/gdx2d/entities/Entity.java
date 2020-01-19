package com.igrmm.gdx2d.entities;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.igrmm.gdx2d.DynamicGameObject;
import com.igrmm.gdx2d.GameObject;

public abstract class Entity extends GameObject {
	protected final RectangleMapObject rectangleMapObject;

	public Entity(RectangleMapObject rectangleMapObject) {
		super(rectangleMapObject.getRectangle());
		this.rectangleMapObject = rectangleMapObject;
	}

	public String getType() {
		return rectangleMapObject.getProperties().get("type").toString();
	}

	public String getId() {
		return rectangleMapObject.getProperties().get("id").toString();
	}

	public String getName() {
		return rectangleMapObject.getName();
	}

	public boolean isCollidable() {
		return rectangleMapObject.getProperties().get(
				"collidable",
				false,
				Boolean.class
		);
	}

	public void handle(DynamicGameObject dynamicGameObject) {
	}
}
