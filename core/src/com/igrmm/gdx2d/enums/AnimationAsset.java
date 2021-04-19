package com.igrmm.gdx2d.enums;

public enum AnimationAsset {
	PLAYER("images/player.json"),
	PORTAL("images/portal.json");

	private final String path;

	AnimationAsset(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
