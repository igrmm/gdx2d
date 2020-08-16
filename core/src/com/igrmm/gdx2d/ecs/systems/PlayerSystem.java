package com.igrmm.gdx2d.ecs.systems;

import com.igrmm.gdx2d.ecs.EntityManager;
import com.igrmm.gdx2d.ecs.components.BoundingBoxComponent;
import com.igrmm.gdx2d.ecs.components.InputComponent;

public class PlayerSystem implements System {

    @Override
    public void update(EntityManager entityManager) {
        InputComponent inputs = entityManager.playerInputComponent;
        BoundingBoxComponent boundingBox = entityManager.playerBoundingBoxComponent;

        if (inputs.right) {
            boundingBox.x += 5;
        }

        if (inputs.left) {
            boundingBox.x -= 5;
        }

        if (inputs.up) {
            boundingBox.y += 5;
        }

        if (inputs.down) {
            boundingBox.y -= 5;
        }

        resetInputs(inputs);
    }

    private void resetInputs(InputComponent inputs) {
        inputs.right = false;
        inputs.left = false;
        inputs.up = false;
        inputs.down = false;
    }
}
