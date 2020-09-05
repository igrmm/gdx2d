package com.igrmm.gdx2d.ecs.systems;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.igrmm.gdx2d.ecs.Components;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;

import java.util.ArrayList;
import java.util.HashMap;

public class PhysicsSystem implements System {
	private final ArrayList<BoundingBoxComponent> queue = new ArrayList<>();
	private final Rectangle intersection = new Rectangle();

	@Override
	public void update(Components components) {
		HashMap<String, BoundingBoxComponent> dynamicBBoxes = components.dynamicBoundingBoxComponents;
		HashMap<String, BoundingBoxComponent> blockBBoxes = components.blockBoundingBoxComponents;

		for (BoundingBoxComponent dynamicBBox : dynamicBBoxes.values()) {
			for (BoundingBoxComponent blockBBox : blockBBoxes.values()) {
				if (dynamicBBox.overlaps(blockBBox)) {
					queue.add(blockBBox);
				}
			}

			if (!queue.isEmpty()) {
				for (BoundingBoxComponent blockBBox : queue) {
					if (Intersector.intersectRectangles(dynamicBBox, blockBBox, intersection)) {

						//upper left corner
						if (dynamicBBox.x != intersection.x && dynamicBBox.y == intersection.y) {
							if (intersection.width > intersection.height) {
								dynamicBBox.y = intersection.y + intersection.height;
							} else if (intersection.width < intersection.height) {
								dynamicBBox.x = intersection.x - dynamicBBox.width;
							} else if (intersection.width == intersection.height && queue.size() == 1) {
								dynamicBBox.y = intersection.y + intersection.height;
								//dynamicBBox.x = intersection.x - dynamicBBox.width;
							}
						}

						//upper right corner
						else if (dynamicBBox.x == intersection.x && dynamicBBox.y == intersection.y) {
							if (intersection.width > intersection.height) {
								dynamicBBox.y = intersection.y + intersection.height;
							} else if (intersection.width < intersection.height) {
								dynamicBBox.x = intersection.x + intersection.width;
							} else if (intersection.width == intersection.height && queue.size() == 1) {
								dynamicBBox.y = intersection.y + intersection.height;
								//dynamicBBox.x = intersection.x + intersection.width;
							}
						}

						//lower left corner
						else if (dynamicBBox.x != intersection.x && dynamicBBox.y != intersection.y) {
							if (intersection.width > intersection.height) {
								dynamicBBox.y = intersection.y - dynamicBBox.height;
							} else if (intersection.width < intersection.height) {
								dynamicBBox.x = intersection.x - dynamicBBox.width;
							} else if (intersection.width == intersection.height && queue.size() == 1) {
								dynamicBBox.y = intersection.y - dynamicBBox.height;
								//dynamicBBox.x = intersection.x - dynamicBBox.width;
							}
						}

						//lower right corner
						else if (dynamicBBox.x == intersection.x && dynamicBBox.y != intersection.y) {
							if (intersection.width > intersection.height) {
								dynamicBBox.y = intersection.y - dynamicBBox.height;
							} else if (intersection.width < intersection.height) {
								dynamicBBox.x = intersection.x + intersection.width;
							} else if (intersection.width == intersection.height && queue.size() == 1) {
								dynamicBBox.y = intersection.y - dynamicBBox.height;
								//dynamicBBox.x = intersection.x + intersection.width;
							}
						}
					}
				}
			}
			queue.clear();
		}
	}
}
