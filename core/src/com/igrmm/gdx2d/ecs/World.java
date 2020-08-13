package com.igrmm.gdx2d.ecs;

import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.InputComponent;
import com.igrmm.gdx2d.ecs.systems.PlayerSystem;
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
    public final InputComponent playerInputComponent = new InputComponent();

    public World() {

        /* INITIALIZE PLAYER */
        int player = getUniqueId();
        playerBoundingBoxComponent = new BoundingBoxComponent(100, 100, 32, 32);

        /* CREATE SYSTEMS */
        systems.add(new PlayerSystem());
    }

    public int getUniqueId() {
        int uniqueId = idCounter;
        idCounter++;
        return uniqueId;
    }

    public void update(float delta) {
        for (System system : systems) {
            system.update(this);
        }
    }
}
