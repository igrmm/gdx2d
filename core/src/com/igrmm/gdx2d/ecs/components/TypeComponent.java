package com.igrmm.gdx2d.ecs.components;

import com.igrmm.gdx2d.enums.Type;

public class TypeComponent implements Component {
	public final Type type;

	public TypeComponent(Type type) {
		this.type = type;
	}
}
