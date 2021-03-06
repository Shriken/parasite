package Parasite.ui.widget;

import Parasite.ui.UI;
import Parasite.sim.Simulation;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class UIWidget {

	protected final int x, y;
	protected final int width, height;

	protected Simulation sim;

	public UIWidget(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth()  { return width;  }
	public int getHeight() { return height; }
	public void setSimulation(Simulation sim) { this.sim = sim; }

	// render w.r.t. the widget; (0,0) is the top left corner.
	public abstract void render(Graphics2D g);

	// handle mouse events
	public abstract void mouseClicked(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
}
