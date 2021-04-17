package com.igrmm.gdx2d.ecs.components;

import com.igrmm.gdx2d.enums.Waypoint;

public class WaypointComponent implements Component {
	public final Waypoint waypoint;

	public WaypointComponent(Waypoint waypoint) {
		this.waypoint = waypoint;
	}
}
