package com.igrmm.gdx2d.ecs;

import com.badlogic.gdx.math.Rectangle;

public class CRigidBody extends Rectangle implements Component {
	public boolean isCollidable = false;
	public boolean isHurtable = false;
	public boolean isHurtful = false;
}
