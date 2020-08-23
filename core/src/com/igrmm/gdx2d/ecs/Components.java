package com.igrmm.gdx2d.ecs;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.ecs.components.AnimationComponent;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.GraphicsContextComponent;
import com.igrmm.gdx2d.ecs.components.InputComponent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Components {
    public final HashSet<String> IDs = new HashSet<>();
    public final String playerID = getID();

    public final InputComponent inputComponent;
    public final GraphicsContextComponent graphicsContextComponent;
    public final HashMap<String, AnimationComponent> animationComponents;
    public final HashMap<String, BoundingBoxComponent> dynamicBoundingBoxComponents;
    public final HashMap<String, BoundingBoxComponent> blockBoundingBoxComponents;

    private Components(TiledMap map) {
        inputComponent = new InputComponent();
        graphicsContextComponent = new GraphicsContextComponent(map);
        animationComponents = new HashMap<>();
        dynamicBoundingBoxComponents = new HashMap<>();
        blockBoundingBoxComponents = new HashMap<>();
    }

    public String getID() {
        String ID = UUID.randomUUID().toString();
        IDs.add(ID);
        return ID;
    }

    public static Components fromAssets(Assets assets) {
        TiledMap map = assets.get("maps/start.tmx");
        Components components = new Components(map);

        /* PLAYER */
        components.animationComponents.put(components.playerID, new AnimationComponent(assets));
        components.dynamicBoundingBoxComponents.put(components.playerID, new BoundingBoxComponent(100, 100, 32, 32));

        /* GRAPHICS */
        String graphicsID = components.getID();

        /* GET OBJECTS FROM TILED MAP */
        MapGroupLayer objectsLayer = (MapGroupLayer) map.getLayers().get("objects");
        for (MapLayer mapLayer : objectsLayer.getLayers()) {
            for (MapObject mapObject : mapLayer.getObjects()) {
                String type = mapObject.getProperties().get("type").toString();
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;

                switch (type) {
                    case "waypoint":
                        break;

                    case "block":
                        String blockID = components.getID();
                        Rectangle r = rectangleMapObject.getRectangle();
                        components.blockBoundingBoxComponents.put(blockID, new BoundingBoxComponent(r));
                        break;

                    default:
                        throw new NullPointerException();
                }
            }
        }

        return components;
    }
}