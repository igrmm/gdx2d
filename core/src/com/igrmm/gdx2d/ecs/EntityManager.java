package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.ecs.components.CPosition;
import com.igrmm.gdx2d.ecs.components.CRigidBody;
import com.igrmm.gdx2d.ecs.entities.Entity;

import java.util.HashMap;
import java.util.HashSet;

public class EntityManager {
	private int idCounter;
	public final HashSet<Entity> entities = new HashSet<>();
	public final HashMap<Integer, CPosition> cPositionHashMap = new HashMap<>();
	public final HashMap<Integer, CRigidBody> cRigidBodyHashMap = new HashMap<>();

	public int getUniqueId() {
		int uniqueId = idCounter;
		idCounter++;
		return uniqueId;
	}
}
