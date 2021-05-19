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
import com.igrmm.gdx2d.Gdx2D;
import com.igrmm.gdx2d.ecs.AnimationData;
import com.igrmm.gdx2d.ecs.ComponentFactory;
import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.*;
import com.igrmm.gdx2d.ecs.entities.Core;
import com.igrmm.gdx2d.ecs.entities.Player;
import com.igrmm.gdx2d.enums.AnimationAsset;
import com.igrmm.gdx2d.enums.EntityType;

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
		TiledMap tiledMap = game.assets.getTiledMap(game.mapAsset);

		Core.spawn(game, entityManager, disposables, tiledMap);
		Player.spawn(game.assets, entityManager);
		spawnVirtualButtons(entityManager);

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

		subSystems.clear();
		subSystems.add(new InputSubSystem());
		subSystems.add(new PortalSubSystem());
		subSystems.add(new GravitySubSystem());
		subSystems.add(new BlockSubSystem());
		subSystems.add(new PhysicsSubSystem());
		subSystems.add(new CameraControlSubSystem());
		subSystems.add(new RenderingSubSystem());
		subSystems.add(new DebugSubSystem());
	}

	private void spawnVirtualButtons(EntityManager entityManager) {
		float SCALE = 3.0f;
		String initialAnimation = "up";
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float PADDING = 5.0f * screenWidth / 100.0f;

		/* LEFT BUTTON */
		String vLeftBtnUUID = entityManager.virtualLeftButtonUUID;
		entityManager.addComponent(vLeftBtnUUID, new TypeComponent(EntityType.VIRTUAL_LEFT_BUTTON));
		entityManager.addComponent(vLeftBtnUUID, new AnimationScaleComponent(SCALE));
		AnimationData vLeftBtnAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_LEFT_BUTTON);
		UIAnimationComponent vLeftBtnUIAnimationC = new UIAnimationComponent(vLeftBtnAnimationData, initialAnimation);
		entityManager.addComponent(vLeftBtnUUID, vLeftBtnUIAnimationC);
		TextureRegion vLeftBtnTex = vLeftBtnUIAnimationC.getKeyFrame(0.0f);

		//relative to camera position
		BoundingBoxComponent vLeftBtnBBoxC = new BoundingBoxComponent(
				screenWidth / 2.0f - PADDING,
				screenHeight / 2.0f - PADDING,
				vLeftBtnTex.getRegionWidth() * SCALE,
				vLeftBtnTex.getRegionHeight() * SCALE
		);
		entityManager.addComponent(vLeftBtnUUID, vLeftBtnBBoxC);

		/* RIGHT BUTTON */
		String vRightBtnUUID = entityManager.virtualRightButtonUUID;
		entityManager.addComponent(vRightBtnUUID, new TypeComponent(EntityType.VIRTUAL_RIGHT_BUTTON));
		entityManager.addComponent(vRightBtnUUID, new AnimationScaleComponent(SCALE));
		AnimationData vRightBtnAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_RIGHT_BUTTON);
		UIAnimationComponent vRightBtnUIAnimationC = new UIAnimationComponent(vRightBtnAnimationData, initialAnimation);
		entityManager.addComponent(vRightBtnUUID, vRightBtnUIAnimationC);
		TextureRegion vRightBtnTex = vRightBtnUIAnimationC.getKeyFrame(0.0f);

		//relative to camera position
		BoundingBoxComponent vRightBtnBBoxC = new BoundingBoxComponent(
				vLeftBtnBBoxC.bBox.x - vLeftBtnBBoxC.bBox.width - PADDING,
				screenHeight / 2.0f - PADDING,
				vRightBtnTex.getRegionWidth() * SCALE,
				vRightBtnTex.getRegionHeight() * SCALE
		);
		entityManager.addComponent(vRightBtnUUID, vRightBtnBBoxC);

		/* B BUTTON */
		String vBBtnUUID = entityManager.virtualBButtonUUID;
		entityManager.addComponent(vBBtnUUID, new TypeComponent(EntityType.VIRTUAL_B_BUTTON));
		entityManager.addComponent(vBBtnUUID, new AnimationScaleComponent(SCALE));
		AnimationData vBBtnAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_B_BUTTON);
		UIAnimationComponent vBBtnUIAnimationC = new UIAnimationComponent(vBBtnAnimationData, initialAnimation);
		entityManager.addComponent(vBBtnUUID, vBBtnUIAnimationC);
		TextureRegion vBBtnTex = vBBtnUIAnimationC.getKeyFrame(0.0f);

		//relative to camera position
		BoundingBoxComponent vBBtnBBoxC = new BoundingBoxComponent(
				vBBtnTex.getRegionWidth() * SCALE + PADDING - screenWidth / 2.0f,
				screenHeight / 2.0f - PADDING,
				vBBtnTex.getRegionWidth() * SCALE,
				vBBtnTex.getRegionHeight() * SCALE
		);
		entityManager.addComponent(vBBtnUUID, vBBtnBBoxC);

		/* A BUTTON */
		String vABtnUUID = entityManager.virtualAButtonUUID;
		entityManager.addComponent(vABtnUUID, new TypeComponent(EntityType.VIRTUAL_A_BUTTON));
		entityManager.addComponent(vABtnUUID, new AnimationScaleComponent(SCALE));
		AnimationData vABtnAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_A_BUTTON);
		UIAnimationComponent vABtnUIAnimationC = new UIAnimationComponent(vABtnAnimationData, initialAnimation);
		entityManager.addComponent(vABtnUUID, vABtnUIAnimationC);
		TextureRegion vABtnTex = vABtnUIAnimationC.getKeyFrame(0.0f);

		//relative to camera position
		BoundingBoxComponent vABtnBBoxC = new BoundingBoxComponent(
				vBBtnBBoxC.bBox.x + PADDING + vABtnTex.getRegionWidth() * SCALE,
				screenHeight / 2.0f - PADDING,
				vABtnTex.getRegionWidth() * SCALE,
				vABtnTex.getRegionHeight() * SCALE
		);
		entityManager.addComponent(vABtnUUID, vABtnBBoxC);
	}
}
