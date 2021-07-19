package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.igrmm.gdx2d.Assets;
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.Saves;
import com.igrmm.gdx2d.ecs.AnimationData;
import com.igrmm.gdx2d.ecs.ComponentFactory;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.ecs.entities.Core;
import com.igrmm.gdx2d.ecs.entities.Player;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.EntityType;
import com.igrmm.gdx2d.enums.MapAsset;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class InitializeSubSystem implements SubSystem {
	private final Gdx2D game;
	private final LinkedHashSet<SubSystem> subSystems;
	private final List<Disposable> disposables;

	public InitializeSubSystem(Gdx2D game, LinkedHashSet<SubSystem> subSystems, List<Disposable> disposables) {
		this.game = game;
		this.subSystems = subSystems;
		this.disposables = disposables;
	}

	@Override
	public void update(EntityManager entityManager, float delta) {

		// Retrieve tiled map using saves
		Assets assets = game.assets;
		Saves saves = game.saves;
		MapAsset mapAsset = saves.getMapComponent().mapAsset;
		TiledMap tiledMap = assets.getTiledMap(mapAsset);

		// Get entities and components from tiled map
		MapGroupLayer objectsLayer = (MapGroupLayer) tiledMap.getLayers().get("objects");
		for (MapLayer mapLayer : objectsLayer.getLayers()) {
			for (MapObject mapObject : mapLayer.getObjects()) {
				String entityUUID = entityManager.createEntity();
				Rectangle bBox = ((RectangleMapObject) mapObject).getRectangle();
				entityManager.addComponent(entityUUID, new BoundingBoxComponent(bBox));
				MapProperties properties = mapObject.getProperties();
				Iterator<String> iterator = properties.getKeys();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object value = properties.get(key);
					Component component = ComponentFactory.getComponent(key, value, game.assets);
					if (component != null)
						entityManager.addComponent(entityUUID, component);
				}
			}
		}

		// Spawn important entities
		Core.spawn(game, entityManager, disposables, tiledMap);
		Player.spawn(game, entityManager);
		spawnVirtualButtons(entityManager);

		// Create subsystems
		subSystems.clear();
		subSystems.add(new PlayerSubSystem());
		subSystems.add(new ProjectileSubSystem());
		subSystems.add(new PortalSubSystem());
		subSystems.add(new PhysicsSubSystem());
		subSystems.add(new BlockSubSystem());
		subSystems.add(new CollisionSubSystem());
		subSystems.add(new CameraControlSubSystem());
		subSystems.add(new RenderingSubSystem());
		subSystems.add(new DebugSubSystem());
	}

	private void spawnVirtualButtons(EntityManager entityManager) {
		//DPI STUFF
		final float BUTTON_SIZE_CM = 1.5f;
		final float BUTTON_SIZE_PX = 16.0f;
		final float PIXEL_SIZE_CM = 2.0f / (Gdx.graphics.getPpcX() + Gdx.graphics.getPpcY());
		final float SCALE = BUTTON_SIZE_CM / (BUTTON_SIZE_PX * PIXEL_SIZE_CM);

		String initialAnimation = "up";
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		final float PADDING = 3.0f * screenWidth / 100.0f;

		/* LEFT BUTTON */
		AnimationData vLeftBtnAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_LEFT_BUTTON);
		UIAnimationComponent vLeftBtnUIAnimationC = new UIAnimationComponent(vLeftBtnAnimationData, initialAnimation);
		TextureRegion vLeftBtnTex = vLeftBtnUIAnimationC.getKeyFrame(0.0f);
		if (vLeftBtnTex.getRegionHeight() != BUTTON_SIZE_PX || vLeftBtnTex.getRegionWidth() != BUTTON_SIZE_PX) {
			throw new IllegalStateException("Virtual button size is not regular.");
		}

		String vLeftBtnUUID = entityManager.virtualLeftButtonUUID;
		entityManager.addComponent(vLeftBtnUUID, new TypeComponent(EntityType.VIRTUAL_LEFT_BUTTON));
		entityManager.addComponent(vLeftBtnUUID, vLeftBtnUIAnimationC);
		vLeftBtnUIAnimationC.scale = SCALE;

		//relative to camera position
		BoundingBoxComponent vLeftBtnBBoxC = new BoundingBoxComponent(
				screenWidth / 2.0f - PADDING,
				screenHeight / 2.0f - PADDING,
				BUTTON_SIZE_PX * SCALE,
				BUTTON_SIZE_PX * SCALE
		);
		entityManager.addComponent(vLeftBtnUUID, vLeftBtnBBoxC);

		/* RIGHT BUTTON */
		AnimationData vRightBtnAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_RIGHT_BUTTON);
		UIAnimationComponent vRightBtnUIAnimationC = new UIAnimationComponent(vRightBtnAnimationData, initialAnimation);
		TextureRegion vRightBtnTex = vRightBtnUIAnimationC.getKeyFrame(0.0f);
		if (vRightBtnTex.getRegionHeight() != BUTTON_SIZE_PX || vRightBtnTex.getRegionWidth() != BUTTON_SIZE_PX) {
			throw new IllegalStateException("Virtual button size is not regular.");
		}

		String vRightBtnUUID = entityManager.virtualRightButtonUUID;
		entityManager.addComponent(vRightBtnUUID, new TypeComponent(EntityType.VIRTUAL_RIGHT_BUTTON));
		entityManager.addComponent(vRightBtnUUID, vRightBtnUIAnimationC);
		vRightBtnUIAnimationC.scale = SCALE;

		//relative to camera position
		BoundingBoxComponent vRightBtnBBoxC = new BoundingBoxComponent(
				vLeftBtnBBoxC.bBox.x - BUTTON_SIZE_PX * SCALE - PADDING,
				screenHeight / 2.0f - PADDING,
				BUTTON_SIZE_PX * SCALE,
				BUTTON_SIZE_PX * SCALE
		);
		entityManager.addComponent(vRightBtnUUID, vRightBtnBBoxC);

		/* B BUTTON */
		AnimationData vBBtnAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_B_BUTTON);
		UIAnimationComponent vBBtnUIAnimationC = new UIAnimationComponent(vBBtnAnimationData, initialAnimation);
		TextureRegion vBBtnTex = vBBtnUIAnimationC.getKeyFrame(0.0f);
		if (vBBtnTex.getRegionHeight() != BUTTON_SIZE_PX || vBBtnTex.getRegionWidth() != BUTTON_SIZE_PX) {
			throw new IllegalStateException("Virtual button size is not regular.");
		}

		String vBBtnUUID = entityManager.virtualBButtonUUID;
		entityManager.addComponent(vBBtnUUID, new TypeComponent(EntityType.VIRTUAL_B_BUTTON));
		entityManager.addComponent(vBBtnUUID, vBBtnUIAnimationC);
		vBBtnUIAnimationC.scale = SCALE;

		//relative to camera position
		BoundingBoxComponent vBBtnBBoxC = new BoundingBoxComponent(
				BUTTON_SIZE_PX * SCALE + PADDING - screenWidth / 2.0f,
				screenHeight / 2.0f - PADDING,
				BUTTON_SIZE_PX * SCALE,
				BUTTON_SIZE_PX * SCALE
		);
		entityManager.addComponent(vBBtnUUID, vBBtnBBoxC);

		/* A BUTTON */
		AnimationData vABtnAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_A_BUTTON);
		UIAnimationComponent vABtnUIAnimationC = new UIAnimationComponent(vABtnAnimationData, initialAnimation);
		TextureRegion vABtnTex = vABtnUIAnimationC.getKeyFrame(0.0f);
		if (vABtnTex.getRegionHeight() != BUTTON_SIZE_PX || vABtnTex.getRegionWidth() != BUTTON_SIZE_PX) {
			throw new IllegalStateException("Virtual button size is not regular.");
		}

		String vABtnUUID = entityManager.virtualAButtonUUID;
		entityManager.addComponent(vABtnUUID, new TypeComponent(EntityType.VIRTUAL_A_BUTTON));
		entityManager.addComponent(vABtnUUID, vABtnUIAnimationC);
		vABtnUIAnimationC.scale = SCALE;

		//relative to camera position
		BoundingBoxComponent vABtnBBoxC = new BoundingBoxComponent(
				vBBtnBBoxC.bBox.x + PADDING + BUTTON_SIZE_PX * SCALE,
				screenHeight / 2.0f - PADDING,
				BUTTON_SIZE_PX * SCALE,
				BUTTON_SIZE_PX * SCALE
		);
		entityManager.addComponent(vABtnUUID, vABtnBBoxC);
	}
}
