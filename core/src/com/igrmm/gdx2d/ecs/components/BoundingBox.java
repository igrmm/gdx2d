package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.math.Rectangle;

public class BoundingBox extends Rectangle implements Component {
    public BoundingBox(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public BoundingBox(Rectangle rect) {
        super(rect);
    }

    public BoundingBox() {
    }
}
