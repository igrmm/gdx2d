package com.igrmm.gdx2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.igrmm.gdx2d.ecs.components.MapComponent;
import com.igrmm.gdx2d.ecs.components.MovementComponent;
import com.igrmm.gdx2d.ecs.components.WaypointComponent;

public class Saves {
	public static final String SAVE_PATH = "save.json";

	private final Json json = new Json();

	private SaveData saveData = new SaveData();

	private boolean isDataLoaded = false;

	public void save() {
		String txt = json.prettyPrint(saveData);
		FileHandle file = Gdx.files.local(SAVE_PATH);
		file.writeString(txt, false);
	}

	public void load() {
		FileHandle file = Gdx.files.local(SAVE_PATH);
		if (file.exists()) {
			saveData = new Json().fromJson(SaveData.class, file);
			isDataLoaded = true;
		}
	}

	public boolean isDataLoaded() {
		return isDataLoaded;
	}

	public void setMapComponent(MapComponent mapComponent) {
		saveData.mapComponent = mapComponent;
	}

	public MapComponent getMapComponent() {
		return saveData.mapComponent;
	}

	public void setWaypointComponent(WaypointComponent waypointComponent) {
		saveData.waypointComponent = waypointComponent;
	}

	public WaypointComponent getWaypointComponent() {
		return saveData.waypointComponent;
	}

	public MovementComponent getMovementComponent() {
		return saveData.movementComponent;
	}

	private static class SaveData {
		MapComponent mapComponent = new MapComponent();
		WaypointComponent waypointComponent = new WaypointComponent();
		final MovementComponent movementComponent = new MovementComponent();
	}
}
