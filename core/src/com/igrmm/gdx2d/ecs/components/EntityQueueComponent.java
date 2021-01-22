package com.igrmm.gdx2d.ecs.components;

import java.util.HashSet;
import java.util.Set;

public class EntityQueueComponent implements Component {
	public final Set<String> queue = new HashSet<>();
}
