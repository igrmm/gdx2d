package com.igrmm.gdx2d.ecs;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Collision {
	private final Vector2 normal;
	private final float time;
	private final Vector2 velocity;

	public Collision(Rectangle dynamicRectangle, Rectangle staticRectangle, Vector2 velocity) {
		normal = new Vector2();
		time = getTime(dynamicRectangle, staticRectangle, velocity);
		this.velocity = velocity;
	}

	private float getTime(Rectangle dynamicRectangle, Rectangle staticRectangle, Vector2 velocity) {
		if (velocity.x == 0 && velocity.y == 0)
			return 1.0f;

		/*
			    Ray equation
		        contact = rayOrigin + rayDirection * tMin
		*/

		// ray origin is the center of dynamic rectangle
		float rayOriginX = dynamicRectangle.x + dynamicRectangle.width / 2.0f;
		float rayOriginY = dynamicRectangle.y + dynamicRectangle.height / 2.0f;

		//ray direction is the velocity of dynamic rect
		float rayDirectionX = velocity.x;
		float rayDirectionY = velocity.y;

		//expanded rect is the target rect (static) plus the size of dynamic rect
		float expandedRectangleWidth = staticRectangle.width + dynamicRectangle.width;
		float expandedRectangleHeight = staticRectangle.height + dynamicRectangle.height;
		float expandedRectangleX = staticRectangle.x + (staticRectangle.width - expandedRectangleWidth) / 2.0f;
		float expandedRectangleY = staticRectangle.y + (staticRectangle.height - expandedRectangleHeight) / 2.0f;

		//need for verification of division by zero
		float invRayDirectionX = 1.0f / rayDirectionX;
		float invRayDirectionY = 1.0f / rayDirectionY;

		// t value for ray to contact line defined by expandedRectangleX
		float t0X = (expandedRectangleX - rayOriginX) * invRayDirectionX;

		// t value for ray to contact line defined by expandedRectangleY
		float t0Y = (expandedRectangleY - rayOriginY) * invRayDirectionY;

		// t value for ray to contact line defined by (expandedRectangleX + expandedRectangleWidth)
		float t1X = (expandedRectangleX + expandedRectangleWidth - rayOriginX) * invRayDirectionX;

		// t value for ray to contact line defined by (expandedRectangleY + expandedRectangleHeight)
		float t1Y = (expandedRectangleY + expandedRectangleHeight - rayOriginY) * invRayDirectionY;

		if (Float.isNaN(t0X) || Float.isNaN(t0Y)) return 1.0f;
		if (Float.isNaN(t1X) || Float.isNaN(t1Y)) return 1.0f;

		//swap values
		if (t0X > t1X) {
			float tmp = t0X;
			t0X = t1X;
			t1X = tmp;
		}

		//swap values
		if (t0Y > t1Y) {
			float tmp = t0Y;
			t0Y = t1Y;
			t1Y = tmp;
		}

		//non-contact condition
		if (t0X > t1Y || t0Y > t1X) return 1.0f;

		//t value for ray to first contact with rectangle
		float tMin = Math.max(t0X, t0Y);

		//t value for ray to last contact with rectangle
		float tMax = Math.min(t1X, t1Y);
		if (tMax < 0) return 1.0f;

		//set contact normal for collision solving
		if (t0X > t0Y) {
			if (rayOriginX > expandedRectangleX)
				normal.set(1, 0);
			else
				normal.set(-1, 0);

		} else if (t0X < t0Y) {
			if (rayOriginY > expandedRectangleY)
				normal.set(0, 1);
			else
				normal.set(0, -1);
		}

		return tMin;
	}

	public final boolean hasOccurred() {
		return (time >= 0.0f && time < 1.0f);
	}

	public boolean resolve() {
		if (hasOccurred()) {
			velocity.x += Math.abs(velocity.x) * normal.x * (1.0f - time);
			velocity.y += Math.abs(velocity.y) * normal.y * (1.0f - time);
			return true;
		} else {
			return false;
		}
	}
}
