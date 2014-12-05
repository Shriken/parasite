package Parasite.sim.entity;

import java.awt.Color;
import java.awt.Graphics2D;

public class Entity {

	public double x, y;
	public double speed;
	public double heading;

	public Entity() {
		this(0, 0);
	}

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
		speed = 1;
	}

	public void render(Graphics2D g) {
		g.rotate(heading);
		g.setColor(new Color(0, 100, 255));
		g.fillRect(-12, -12, 24, 24);
		g.rotate(-heading);
	}
}