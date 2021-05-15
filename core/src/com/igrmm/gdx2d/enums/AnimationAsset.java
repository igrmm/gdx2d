package com.igrmm.gdx2d.enums;

public enum AnimationAsset {
	PLAYER("animations/player.json"),
	PORTAL("animations/portal.json"),
	BULLET("animations/bullet.json"),
	VIRTUAL_LEFT_BUTTON("animations/virtual_left_button.json"),
	VIRTUAL_RIGHT_BUTTON("animations/virtual_right_button.json"),
	VIRTUAL_A_BUTTON("animations/virtual_a_button.json"),
	VIRTUAL_B_BUTTON("animations/virtual_b_button.json");

	private final String path;

	AnimationAsset(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
