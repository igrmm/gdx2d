package com.igrmm.gdx2d.ecs;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;

public class WorldFactory {
    public static World make(TiledMap map) {
        World world = new World();
        MapGroupLayer entitiesLayer = (MapGroupLayer) map.getLayers().get("entities");

        for (MapLayer mapLayer : entitiesLayer.getLayers()) {
            for (MapObject mapObject : mapLayer.getObjects()) {
                String type = mapObject.getProperties().get("type").toString();
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;

                switch (type) {
                    case "waypoint":
                        break;

                    case "block":
                        int blockId = world.getUniqueId();
                        world.entities.add(blockId);
                        world.blockBoundingBoxes.put(blockId, new BoundingBoxComponent(rectangleMapObject.getRectangle()));
                        break;

                    default:
                        throw new NullPointerException();
                }
            }
        }

        return world;
    }
}
