package com.igrmm.gdx2d.ecs.components;

import com.igrmm.gdx2d.ecs.Collision;

import java.util.ArrayList;
import java.util.List;

public class BroadPhaseCollisionComponent implements Component {
	public List<Collision> collisions = new ArrayList<>();
}
