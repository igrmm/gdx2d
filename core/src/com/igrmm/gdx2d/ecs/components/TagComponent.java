package com.igrmm.gdx2d.ecs.components;

import com.igrmm.gdx2d.ecs.Tag;

public class TagComponent implements Component {
	public final Tag tag;

	public TagComponent(Tag tag) {
		this.tag = tag;
	}
}
