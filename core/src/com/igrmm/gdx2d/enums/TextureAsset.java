package com.igrmm.gdx2d.enums;

public enum TextureAsset {
	PLAYER("images/player.png");

	private final String path;

	TextureAsset(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
