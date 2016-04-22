package Parasite.sim;

import Parasite.sim.Level;
import Parasite.sim.Simulation;
import Parasite.ui.UI;
import Parasite.util.Vector2d;
import Parasite.util.Line;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;

public class VisionShape {
	public static Shape getShape(Level level, Vector2d pos) {
		// sort wall verts by angle
		ArrayList<Vector2d> points = level.getWallVerts();
		double[] angles = new double[points.size()];
		for (int i = 0; i < angles.length; i++) {
			angles[i] = points.get(i).clone().sub(pos).angle();
		}
		Arrays.sort(angles);

		// get directions from angles
		UI ui = UI.getInstance();
		Vector2d[] directions = new Vector2d[angles.length];
		for (int i = 0; i < angles.length; i++) {
			int maxDimension = Math.max(
				ui.canvasWidth,
				ui.canvasHeight
			);
			directions[i] = Vector2d
				.fromAngle(angles[i])
				.scale(maxDimension / Simulation.RENDER_SCALE);
		}

		// convert directions to rays
		ArrayList<Line> rays = new ArrayList<Line>();
		for (Vector2d direction : directions) {
			rays.add(new Line(pos.clone(), direction.clone()));
		}

		// clip rays to closest wall
		ArrayList<Line> walls = level.getWallEdges();
		for (Line ray : rays) {
			double shortestDist = ray.length();
			for (Line wall : walls) {
				shortestDist = Math.min(
					shortestDist,
					ray.intersectDist(wall)
				);
			}
			ray.dim.normalize(shortestDist);
			ray.dim.scale(Simulation.RENDER_SCALE);
		}

		// construct shape from clipped rays
		Path2D.Double polygon = new Path2D.Double();
		Line ray = rays.get(0);
		polygon.moveTo(ray.p1.x + ray.dim.x, ray.p1.y + ray.dim.y);
		for (int i = 1; i < rays.size() - 1; i++) {
			ray = rays.get(i);
			polygon.lineTo(ray.p1.x + ray.dim.x, ray.p1.y + ray.dim.y);
		}
		ray = rays.get(rays.size() - 1);
		polygon.lineTo(ray.p1.x + ray.dim.x, ray.p1.y + ray.dim.y);

		return polygon;
	}
}
