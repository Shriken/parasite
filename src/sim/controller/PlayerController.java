package Parasite.sim.controller;

import Parasite.ui.UIEvent;
import Parasite.ui.EventCode;
import Parasite.sim.Simulation;
import Parasite.sim.entity.Entity;
import Parasite.sim.entity.ParasiteEntity;

import java.awt.Color;
import java.util.ArrayList;

public class PlayerController extends Controller {

	private boolean moveUp = false;
	private boolean moveDown = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;

	private Entity mainHost;

	// Debug vars
	private boolean colliding;

	public PlayerController(Entity entity) {
		super(entity);
		mainHost = entity;
	}

	public void update() {
		Entity mainHost = controlled.get(controlled.size() - 1);

		// check to see if mainHost is a leaper, to consolidate next if
		ParasiteEntity leaper = null;
		if (mainHost instanceof ParasiteEntity)
			leaper = (ParasiteEntity) mainHost;

		if (leaper != null && leaper.leaping) {
			double lookAngle = leaper.getLookAngle();

			// move entities in leap direction
			for (Entity entity : controlled) {
				entity.x += Math.cos(lookAngle) *
					        leaper.maxSpeed * 8;
				entity.y -= Math.sin(lookAngle) *
					        leaper.maxSpeed * 8;
			}

			// if leap timed out, end leap
			if (System.currentTimeMillis() - leaper.leapStartTime >=
				leaper.leapMaxDuration) {
				leaper.leaping = false;
			}
		} else {
			for (Entity entity : controlled) {
				entity.vx = 0;
				entity.vy = 0;

				if (moveUp)    entity.vy += mainHost.maxSpeed;
				if (moveLeft)  entity.vx -= mainHost.maxSpeed;
				if (moveDown)  entity.vy -= mainHost.maxSpeed;
				if (moveRight) entity.vx += mainHost.maxSpeed;

				entity.x += entity.vx;
				entity.y += entity.vy;
			}
		}

		// detect collisons
		Simulation sim = Simulation.getSimulation();
		ArrayList<Entity> allEntities = sim.entities;
		for (Entity entity : allEntities) {
			// TODO account for the collidee being a possessed entity
			if (collidingWith(entity)) {
				collide(entity);
			}
		}

		if (mainHost instanceof ParasiteEntity) {
			if (colliding) {
				mainHost.bodyColor = new Color(255, 100, 100);
			} else {
				mainHost.bodyColor = Color.RED;
			}
		}

	}

	public boolean collidingWith(Entity entity) {
		double dx = entity.x - mainHost.x;
		double dy = entity.y - mainHost.y;
		double distSq = dx * dx + dy * dy;

		colliding = distSq < Math.pow(mainHost.rad + entity.rad, 2);
		return colliding;
	}

	public void collide(Entity entity) {
		colliding = true;
		if (mainHost instanceof ParasiteEntity) {
			// possess
			entity.isPossessed = true;
			addEntity(entity);
		}
	}

	public void addEntity(Entity entity) {
		controlled.add(entity);
		mainHost = entity;
	}

	public void processEvent(UIEvent e) {
		switch (e.eventCode) {
			case MOVE_UP:
				moveUp = true;
				break;
			case MOVE_LEFT:
				moveLeft = true;
				break;
			case MOVE_DOWN:
				moveDown = true;
				break;
			case MOVE_RIGHT:
				moveRight = true;
				break;
			case STOP_UP:
				moveUp = false;
				break;
			case STOP_LEFT:
				moveLeft = false;
				break;
			case STOP_DOWN:
				moveDown = false;
				break;
			case STOP_RIGHT:
				moveRight = false;
				break;
			case ACTION:
				mainHost.action();
				break;
		}
	}
}
