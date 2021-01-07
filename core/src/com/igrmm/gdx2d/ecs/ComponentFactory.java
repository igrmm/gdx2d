package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.Assets.MapAsset;
import com.igrmm.gdx2d.enums.Type;

public class ComponentFactory {
	public static Component getComponent(String key, Object value) {
		switch (key) {
			case "type":
				for (Type type : Type.values()) {
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

			default:
				return null;
		}
	}
}
