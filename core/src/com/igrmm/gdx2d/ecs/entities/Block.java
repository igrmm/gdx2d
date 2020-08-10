package com.igrmm.gdx2d.ecs.entities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.igrmm.gdx2d.ecs.World;
import com.igrmm.gdx2d.ecs.components.BoundingBox;

public class Block extends Entity{
    public Block(World world, MapObject mapObject) {
        super(world);

        RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
        world.blockBoundingBoxes.put(id, new BoundingBox(rectangleMapObject.getRectangle()));
    }
}
