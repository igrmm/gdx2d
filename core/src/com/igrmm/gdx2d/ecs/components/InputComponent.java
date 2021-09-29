package com.igrmm.gdx2d.ecs.components;

import com.badlogic.gdx.Input;

public class InputComponent implements Component {
	public int rightKey = Input.Keys.D;
	public int leftKey = Input.Keys.A;
	public int actionKey = Input.Keys.K;
	public int jumpKey = Input.Keys.L;

	public int directionInput = 0;
	public boolean jumpInput = false;
	public boolean actionInput = false;
}
