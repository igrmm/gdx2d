package com.igrmm.gdx2d.ecs;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Collision {
	private final Vector2 normal;
	private final float time;
	private final Vector2 speed;

	public Collision(Rectangle dynamicRectangle, Rectangle staticRectangle, Vector2 speed) {
		normal = new Vector2();
		time = getTime(dynamicRectangle, staticRectangle, speed);
		this.speed = speed;
	}

	private float getTime(Rectangle dynamicRectangle, Rectangle staticRectangle, Vector2 speed) {
		if (speed.x == 0.0f && speed.y == 0.0f)
			return 1.0f;

		/*
			    Ray equation
		        contact = rayOrigin + rayDirection * tMin
		*/

		// ray origin is the center of dynamic rectangle
		float rayOriginX = dynamicRectangle.x + dynamicRectangle.width / 2.0f;
		float rayOriginY = dynamicRectangle.y + dynamicRectangle.height / 2.0f;

		//ray direction is the velocity of dynamic rect
		float rayDirectionX = speed.x;
		float rayDirectionY = speed.y;

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
				normal.set(1.0f, 0.0f);
			else
				normal.set(-1.0f, 0.0f);

		} else if (t0X < t0Y) {
			if (rayOriginY > expandedRectangleY)
				normal.set(0.0f, 1.0f);
			else
				normal.set(0.0f, -1.0f);
		}

		return tMin;
	}

	public float getNormalX() {
		return normal.x;
	}

	public float getNormalY() {
		return normal.y;
	}

	public final boolean hasOccurred() {
		return (time >= 0.0f && time < 1.0f);
	}

	public boolean resolve() {
		if (hasOccurred()) {
			speed.x += Math.abs(speed.x) * normal.x * (1.0f - time);
			speed.y += Math.abs(speed.y) * normal.y * (1.0f - time);
			return true;
		} else {
			return false;
		}
	}
}
