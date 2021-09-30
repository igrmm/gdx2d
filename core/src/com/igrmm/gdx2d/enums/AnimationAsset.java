package com.igrmm.gdx2d.enums;

public enum AnimationAsset {
	PLAYER("animations/player.json"),
	PORTAL("animations/portal.json"),
	BULLET("animations/bullet.json");

	private final String path;

	AnimationAsset(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
