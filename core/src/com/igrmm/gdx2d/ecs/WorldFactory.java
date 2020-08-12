package com.igrmm.gdx2d.ecs;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.igrmm.gdx2d.ecs.entities.Block;

public class WorldFactory {
    public static World make(TiledMap map) {
        World world = new World();
        MapGroupLayer entitiesLayer = (MapGroupLayer) map.getLayers().get("entities");

        for (MapLayer mapLayer : entitiesLayer.getLayers()) {
            for (MapObject mapObject : mapLayer.getObjects()) {
                String type = mapObject.getProperties().get("type").toString();

                switch (type) {
                    case "waypoint":
                        break;

                    case "block":
                        Block block = new Block(world, mapObject);
                        break;

                    default:
                        throw new NullPointerException();
                }
            }
        }

        return world;
    }
}
