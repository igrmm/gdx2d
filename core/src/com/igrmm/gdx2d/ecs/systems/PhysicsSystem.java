package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.Components;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;

import java.util.HashMap;

public class PhysicsSystem implements System {

	@Override
	public void update(Components components) {
		HashMap<String, BoundingBoxComponent> dynamicBBoxes = components.dynamicBoundingBoxComponents;
		HashMap<String, BoundingBoxComponent> blockBBoxes = components.blockBoundingBoxComponents;

		for (BoundingBoxComponent dynamicBBox : dynamicBBoxes.values()) {
			if (dynamicBBox.x != dynamicBBox.oldX || dynamicBBox.y != dynamicBBox.oldY) {
				for (BoundingBoxComponent blockBBox : blockBBoxes.values()) {
					Rectangle intersection = new Rectangle();
					if (Intersector.intersectRectangles(dynamicBBox, blockBBox, intersection)) {
						if (intersection.width > intersection.height) {
							if (dynamicBBox.y > dynamicBBox.oldY) {
								dynamicBBox.y = intersection.y - dynamicBBox.height;
							} else {
								dynamicBBox.y = intersection.y + intersection.height;
							}
						} else if (intersection.width < intersection.height) {
							if (dynamicBBox.x > dynamicBBox.oldX) {
								dynamicBBox.x = intersection.x - dynamicBBox.width;
							} else {
								dynamicBBox.x = intersection.x + intersection.width;
							}
						}
					}
				}
			}
		}
	}
}
