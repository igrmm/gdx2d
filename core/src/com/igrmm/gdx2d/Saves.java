package com.igrmm.gdx2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Saves {
	private final JsonValue save;

	public Saves() {
		save = new JsonValue("{}");
	}

	public boolean save(Player player) {
		save.set(new Json().toJson(player));
		FileHandle file = Gdx.files.local("save.json");
		file.writeString(save.toString(), false);
		if (file.exists()) {
			return true;
		} else {
			Gdx.app.error("ERROR SAVING", "save file not found");
			return false;
		}
	}

	public boolean load() {
		FileHandle file = Gdx.files.local("save.json");
		if (file.exists()) {
			save.set(file.readString());
			return true;
		} else {
			Gdx.app.error("ERROR LOADING", "save file not found");
			return false;
		}
	}

	public Player deserializePlayer() {
		try {
			return new Json().fromJson(Player.class, save.toString());
		} catch (Exception ex) {
			Gdx.app.error("JSON DESERIALIZATION ERROR", ex.getMessage());
			return new Player();
		}
	}
}
