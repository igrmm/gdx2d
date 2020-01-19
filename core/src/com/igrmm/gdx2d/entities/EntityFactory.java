package com.igrmm.gdx2d.entities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

public class EntityFactory {

	public static Entity make(MapObject mapObject) {
		RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
		String type = mapObject.getProperties().get("type").toString();

		switch (type) {

			case "waypoint":
				return new Waypoint(rectangleMapObject);

			case "block":
				return new Block(rectangleMapObject);

			default:
				throw new NullPointerException();
		}
	}
}
