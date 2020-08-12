package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.ecs.World;
import com.igrmm.gdx2d.ecs.components.BoundingBox;

public class Player extends Entity {
    public Player(World world) {
        super(world);

        world.playerBoundingBox = new BoundingBox(100, 100, 32, 32);
    }
}
