package com.igrmm.gdx2d.assets;

public enum Map {
	START("maps/start.tmx");

	private final String path;

	Map(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
