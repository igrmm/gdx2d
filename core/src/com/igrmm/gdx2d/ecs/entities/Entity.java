package com.igrmm.gdx2d.ecs.entities;

import com.igrmm.gdx2d.ecs.World;

public class Entity {
    public final int id;

    public Entity(World world) {
        this.id = world.getUniqueId();
        world.entities.add(this);
    }
}
