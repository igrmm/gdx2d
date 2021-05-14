package com.igrmm.gdx2d.ecs.components;

public class AnimationOffsetComponent implements Component {
	public final float spriteOffset;

	public AnimationOffsetComponent(float spriteOffset) {
		this.spriteOffset = spriteOffset;
	}
}
