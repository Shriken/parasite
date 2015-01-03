package Parasite.sim.controller;

import Parasite.sim.Simulation;
import Parasite.sim.entity.Entity;
import Parasite.sim.entity.GoonEntity;

import java.awt.Color;
import java.util.Stack;
import java.util.ArrayList;

public class GoonController extends Controller {

	GoonEntity goon;
	Stack<AIState> states;

	public GoonController(Entity entity) {
		super(entity);
		states = new Stack<AIState>();
	}

	public void update() {
		// don't control the goon if the player is
		if (!goon.isPossessable) return;

		goon.setLookAngle(goon.getLookAngle() + 0.02);

		if (goon.canSee(Simulation.getInstance().parasite)) {
			goon.bodyColor = GoonEntity.CAN_SEE_COLOR;
		} else {
			goon.bodyColor = GoonEntity.DEFAULT_COLOR;
		}
	}

	public void addEntity(Entity entity) {
		if (!(entity instanceof GoonEntity)) {
			System.out.println("tried to add non-goon to GoonController");
			return;
		}

		if (controlled.size() > 0)
			controlled.clear();

		goon = (GoonEntity) entity;
		controlled.add(goon);
	}
}
