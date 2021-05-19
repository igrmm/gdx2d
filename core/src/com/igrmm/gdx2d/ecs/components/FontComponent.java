package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public class FontComponent implements Component, Disposable {
	public final BitmapFont font = new BitmapFont();

	public FontComponent() {
		font.getData().setScale(3.0f);
	}

	@Override
	public void dispose() {
		font.dispose();
	}
}
