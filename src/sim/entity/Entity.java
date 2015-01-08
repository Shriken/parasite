package Parasite.sim.entity;

import Parasite.sim.Location;
import Parasite.sim.Simulation;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Entity {

	protected double x, y;
	public double vx, vy;
	public double rad;

	public double maxSpeed;
	protected double lookAngle;

	public Color bodyColor;
	public boolean isPossessable;

	public boolean dead;

	public Entity() {
		this(0, 0);
	}

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
		maxSpeed = 1;
		isPossessable = true;
		dead = false;
	}

	public abstract void render(Graphics2D g);
	public abstract void action();

	public double getLookAngle() { return lookAngle; }
	public void setLookAngle(double lookAngle) {
		if (lookAngle > Math.PI) lookAngle -= Math.PI * 2;
		this.lookAngle = lookAngle;
	}

	// returns unique instance of current location
	public Location getLocation() {
		return new Location(x, y);
	}

	public void setLocation(Location loc) {
		x = loc.x;
		y = loc.y;
	}

	public void die() {
		dead = true;
	}
}
