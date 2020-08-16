package com.igrmm.gdx2d.ecs;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.ecs.components.AnimationComponent;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.GraphicsContextComponent;
import com.igrmm.gdx2d.ecs.components.InputComponent;
import com.igrmm.gdx2d.ecs.systems.PlayerSystem;
import com.igrmm.gdx2d.ecs.systems.RenderingSystem;
import com.igrmm.gdx2d.ecs.systems.System;

import java.util.HashMap;
import java.util.HashSet;

public class EntityManager {

    /* ENTITIES */
    private int idCounter;
    public final HashSet<Integer> entities = new HashSet<>();

    /* COMPONENTS */
    public final HashMap<Integer, BoundingBoxComponent> blockBoundingBoxes = new HashMap<>();
    public final GraphicsContextComponent graphicsContextComponent;

    /* SYSTEMS */
    public final HashSet<System> systems = new HashSet<>();

    /* PLAYER COMPONENTS*/
    public final AnimationComponent playerAnimationComponent;
    public final BoundingBoxComponent playerBoundingBoxComponent;
    public final InputComponent playerInputComponent = new InputComponent();

    public EntityManager(Assets assets) {

        TiledMap map = assets.get("maps/start.tmx");

        /* CREATE SYSTEMS */
        systems.add(new PlayerSystem());
        systems.add(new RenderingSystem());

        /* PLAYER ENTITY */
        int playerId = getUniqueId();
        entities.add(playerId);
        playerAnimationComponent = new AnimationComponent(assets);
        playerBoundingBoxComponent = new BoundingBoxComponent(100, 100, 32, 32);

        /* GRAPHICS ENTITY */
        int graphicsId = getUniqueId();
        entities.add(graphicsId);
        graphicsContextComponent = new GraphicsContextComponent(map);

        /* GENERATE ENTITIES FROM TILED MAP */
        MapGroupLayer entitiesLayer = (MapGroupLayer) map.getLayers().get("entities");
        for (MapLayer mapLayer : entitiesLayer.getLayers()) {
            for (MapObject mapObject : mapLayer.getObjects()) {
                String type = mapObject.getProperties().get("type").toString();
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;

                switch (type) {
                    case "waypoint":
                        break;

                    case "block":
                        int blockId = getUniqueId();
                        entities.add(blockId);
                        blockBoundingBoxes.put(blockId, new BoundingBoxComponent(rectangleMapObject.getRectangle()));
                        break;

                    default:
                        throw new NullPointerException();
                }
            }
        }
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

    public void dispose() {
        graphicsContextComponent.batch.dispose();
        graphicsContextComponent.mapRenderer.dispose();
    }
}
