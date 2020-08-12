package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.ecs.components.BoundingBox;
import com.igrmm.gdx2d.ecs.entities.Entity;

import java.util.HashMap;
import java.util.HashSet;

public class World {
    private int idCounter;
    public final HashSet<Entity> entities = new HashSet<>();
    public final HashMap<Integer, BoundingBox> blockBoundingBoxes = new HashMap<>();
    public BoundingBox playerBoundingBox = new BoundingBox();

    public int getUniqueId() {
        int uniqueId = idCounter;
        idCounter++;
        return uniqueId;
    }
}
