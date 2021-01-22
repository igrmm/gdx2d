package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class KeyFrameComponent implements Component {
	private final float spriteOffset;
	private TextureRegion keyFrame;

	public KeyFrameComponent(float spriteOffset) {
		this.spriteOffset = spriteOffset;
	}

	public void setKeyFrame(TextureRegion keyFrame) {
		this.keyFrame = keyFrame;
	}

	public TextureRegion getKeyFrame() {
		return keyFrame;
	}

	public float getX(float boundingBoxX) {
		return boundingBoxX - spriteOffset;
	}

	public float getY(float boundingBoxY) {
		return boundingBoxY - spriteOffset;
	}
}
