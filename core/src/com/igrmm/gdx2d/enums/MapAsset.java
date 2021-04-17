package com.igrmm.gdx2d.enums;

public enum MapAsset {
	START("tiled/maps/start.tmx"),
	DESERT("tiled/maps/desert.tmx");

	private final String path;

	MapAsset(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
