package com.igrmm.gdx2d.entities;

import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.DynamicGameObject;

import java.util.ArrayList;

public interface Entity {

	public String getType();

	public String getId();

	public String getName();

	public boolean isCollidable();

	public Rectangle getBounds();

	public void update(float delta, ArrayList<Entity> queue);

	public void handle(DynamicGameObject dynamicGameObject);
}
