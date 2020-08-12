package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.systems.System;

import java.util.HashMap;
import java.util.HashSet;

public class World {

    /* ENTITIES */
    private int idCounter;
    public final HashSet<Integer> entities = new HashSet<>();

    /* COMPONENTS */
    public final HashMap<Integer, BoundingBoxComponent> blockBoundingBoxes = new HashMap<>();

    /* SYSTEMS */
    public final HashSet<System> systems = new HashSet<>();

    /* PLAYER COMPONENTS*/
    public final BoundingBoxComponent playerBoundingBoxComponent;

    public World() {
        int player = getUniqueId();
        playerBoundingBoxComponent = new BoundingBoxComponent(100, 100, 32, 32);
    }

    public int getUniqueId() {
        int uniqueId = idCounter;
        idCounter++;
        return uniqueId;
    }
}
