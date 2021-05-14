package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.enums.*;

public class ComponentFactory {
	public static Component getComponent(String key, Object value, Assets assets) {
		switch (key) {
			case "type":
				for (EntityType type : EntityType.values()) {
					if (type.name().equals(value))
						return new TypeComponent(type);
				}
				throw new NullPointerException("Unexpected [" + key + "]: " + value);

			case "mapAsset":
				for (MapAsset mapAsset : MapAsset.values()) {
					if (mapAsset.name().equals((value)))
						return new MapComponent(mapAsset);
				}
				throw new NullPointerException("Unexpected [" + key + "]: " + value);

			case "animation":
				AnimationAsset animationAsset = AnimationAsset.valueOf(value.toString());
				return new AnimationComponent(assets.getAnimationData(animationAsset));

			case "spriteOffset":
				return new AnimationOffsetComponent(Float.parseFloat(value.toString()));

			case "waypoint":
				for (Waypoint waypoint : Waypoint.values()) {
					if (waypoint.name().equals(value))
						return new WaypointComponent(waypoint);
				}
				throw new NullPointerException("Unexpected [" + key + "]: " + value);

			default:
				return null;
		}
	}
}
