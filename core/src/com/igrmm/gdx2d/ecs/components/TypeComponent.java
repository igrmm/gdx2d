package com.igrmm.gdx2d.ecs.components;

import com.igrmm.gdx2d.enums.EntityType;

public class TypeComponent implements Component {
	public final EntityType type;

	public TypeComponent(EntityType type) {
		this.type = type;
	}
}
