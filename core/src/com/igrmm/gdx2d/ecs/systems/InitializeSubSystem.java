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
		spawnVirtualButtons(entityManager, delta);

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

	private void spawnVirtualButtons(EntityManager entityManager, float delta) {
		float PADDING = 5.0f;
		float SCALE = 3.0f;
		String initialState = "up";

		//LEFT BUTTON
		String virtualLeftButtonUUID = entityManager.virtualLeftButtonUUID;
		entityManager.addComponent(virtualLeftButtonUUID, new TypeComponent(EntityType.VIRTUAL_LEFT_BUTTON));
		entityManager.addComponent(virtualLeftButtonUUID, new AnimationScaleComponent(SCALE));
		AnimationData leftButtonAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_LEFT_BUTTON);
		AnimationComponent leftButtonAnimationC = new AnimationComponent(leftButtonAnimationData);
		leftButtonAnimationC.setAnimation(initialState);
		entityManager.addComponent(virtualLeftButtonUUID, leftButtonAnimationC);
		TextureRegion leftButtonTex = leftButtonAnimationC.getKeyFrame(delta);

		BoundingBoxComponent leftButtonBBoxC = new BoundingBoxComponent(
				Gdx.graphics.getWidth() * PADDING / 100.0f,
				Gdx.graphics.getHeight() * PADDING / 100.0f,
				leftButtonTex.getRegionWidth() * SCALE,
				leftButtonTex.getRegionHeight() * SCALE
		);
		entityManager.addComponent(virtualLeftButtonUUID, leftButtonBBoxC);

		//RIGHT BUTTON
		String virtualRightButtonUUID = entityManager.virtualRightButtonUUID;
		entityManager.addComponent(virtualRightButtonUUID, new TypeComponent(EntityType.VIRTUAL_RIGHT_BUTTON));
		entityManager.addComponent(virtualRightButtonUUID, new AnimationScaleComponent(SCALE));
		AnimationData rightButtonAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_RIGHT_BUTTON);
		AnimationComponent rightButtonAnimationC = new AnimationComponent(rightButtonAnimationData);
		rightButtonAnimationC.setAnimation(initialState);
		entityManager.addComponent(virtualRightButtonUUID, rightButtonAnimationC);
		TextureRegion rightButtonTex = rightButtonAnimationC.getKeyFrame(delta);

		BoundingBoxComponent rightButtonBBoxC = new BoundingBoxComponent(
				leftButtonBBoxC.bBox.x + leftButtonBBoxC.bBox.width + Gdx.graphics.getWidth() * PADDING / 100.0f,
				Gdx.graphics.getHeight() * PADDING / 100.0f,
				rightButtonTex.getRegionWidth() * SCALE,
				rightButtonTex.getRegionHeight() * SCALE
		);
		entityManager.addComponent(virtualRightButtonUUID, rightButtonBBoxC);

		//B BUTTON
		String virtualBButtonUUID = entityManager.virtualBButtonUUID;
		entityManager.addComponent(virtualBButtonUUID, new TypeComponent(EntityType.VIRTUAL_B_BUTTON));
		entityManager.addComponent(virtualBButtonUUID, new AnimationScaleComponent(SCALE));
		AnimationData bButtonAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_B_BUTTON);
		AnimationComponent bButtonAnimationC = new AnimationComponent(bButtonAnimationData);
		bButtonAnimationC.setAnimation(initialState);
		entityManager.addComponent(virtualBButtonUUID, bButtonAnimationC);
		TextureRegion bButtonTex = bButtonAnimationC.getKeyFrame(delta);

		BoundingBoxComponent bButtonBBoxC = new BoundingBoxComponent(
				Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * PADDING / 100.0f - bButtonTex.getRegionWidth() * SCALE,
				Gdx.graphics.getHeight() * PADDING / 100.0f,
				bButtonTex.getRegionWidth() * SCALE,
				bButtonTex.getRegionHeight() * SCALE
		);
		entityManager.addComponent(virtualBButtonUUID, bButtonBBoxC);

		//A BUTTON
		String virtualAButtonUUID = entityManager.virtualAButtonUUID;
		entityManager.addComponent(virtualAButtonUUID, new TypeComponent(EntityType.VIRTUAL_A_BUTTON));
		entityManager.addComponent(virtualAButtonUUID, new AnimationScaleComponent(SCALE));
		AnimationData aButtonAnimationData = game.assets.getAnimationData(AnimationAsset.VIRTUAL_A_BUTTON);
		AnimationComponent aButtonAnimationC = new AnimationComponent(aButtonAnimationData);
		aButtonAnimationC.setAnimation(initialState);
		entityManager.addComponent(virtualAButtonUUID, aButtonAnimationC);
		TextureRegion aButtonTex = aButtonAnimationC.getKeyFrame(delta);

		BoundingBoxComponent aButtonBBoxC = new BoundingBoxComponent(
				bButtonBBoxC.bBox.x - Gdx.graphics.getWidth() * PADDING / 100.0f - aButtonTex.getRegionWidth() * SCALE,
				Gdx.graphics.getHeight() * PADDING / 100.0f,
				aButtonTex.getRegionWidth() * SCALE,
				aButtonTex.getRegionHeight() * SCALE
		);
		entityManager.addComponent(virtualAButtonUUID, aButtonBBoxC);
	}
}
