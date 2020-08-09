package com.igrmm.gdx2d.ecs;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.igrmm.gdx2d.ecs.components.BoundingBox;

public class EntityFactory {
    public static void make(TiledMap map, EntityManager entityManager) {
        MapGroupLayer entitiesLayer = (MapGroupLayer) map.getLayers().get("entities");
        for (MapLayer mapLayer : entitiesLayer.getLayers()) {
            for (MapObject mapObject : mapLayer.getObjects()) {
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
                String type = mapObject.getProperties().get("type").toString();

                switch (type) {
                    case "waypoint":
                        break;

                    case "block":
                        entityManager.blockBoundingBoxes.put(
                                entityManager.getUniqueId(),
                                new BoundingBox(rectangleMapObject.getRectangle())
                        );
                        break;

                    default:
                        throw new NullPointerException();
                }
            }
        }
    }
}
