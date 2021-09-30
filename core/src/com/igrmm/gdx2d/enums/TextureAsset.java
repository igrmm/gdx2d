package com.igrmm.gdx2d.enums;

import java.util.HashMap;
import java.util.Map;

public enum TextureAsset {
	PLAYER("images/player.png"),
	PORTAL("images/portal.png"),
	BULLET("images/bullet.png");

	// Reverse-lookup map for getting a texture asset from an its path
	private static final Map<String, TextureAsset> lookup = new HashMap<>();

	static {
		for (TextureAsset textureAsset : TextureAsset.values()) {
			lookup.put(textureAsset.getPath(), textureAsset);
		}
	}

	private final String path;

	TextureAsset(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public static TextureAsset get(String path) {
		TextureAsset textureAsset = lookup.get(path);
		if (textureAsset != null) {
			return textureAsset;
		} else {
			throw new NullPointerException("wrong texture path: " + path);
		}
	}

	public static boolean contains(String path) {
		for (TextureAsset textureAsset : TextureAsset.values()) {
			if (path.equals(textureAsset.getPath())) return true;
		}
		return false;
	}
}

