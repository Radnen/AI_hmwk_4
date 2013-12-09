/**
	Copyright Andrew Helenius, 2013

	This is merely the Runner for the Pathfinder,
	providing graphical output to the screen.
 **/

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Paver extends Canvas {
	public Paver() {
		setSize(400, 400);
		setBackground(Color.white);
	}
	
	private static Pathfinder path;
	private static Paver paver;
	private static int x1, y1, x2, y2;

	private Node[][] grid;
	private Node pathnode;
	
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("use:\n\tPaver x1 y1 x1 y2");
			return;
		}
		
		x1 = Integer.parseInt(args[0]);
		y1 = Integer.parseInt(args[1]);
		x2 = Integer.parseInt(args[2]);
		y2 = Integer.parseInt(args[3]);
		
		if (x2 >= 299) x2 = 298;
		if (y2 >= 299) y2 = 298;
	
		paver = new Paver();
		path = new Pathfinder();

		path.init(300, 300);
		DoRandom();
		
		Frame mainFrame = new Frame("Pathfinding Demonstration");
		mainFrame.setSize(620, 640);
		mainFrame.add(paver);
		mainFrame.setVisible(true);
		
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}
	
	// pave the path through a random 'terrain'
	public static void DoRandom() {
		int w = 299, h = 299;
		Maze m = new Maze(w, h, 1, 1);
		boolean[][] maze = m.generate();
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (maze[x][y]) path.block(x, y);
			}
		}
		
		path.unblock(x2, y2);
		paver.pathnode = path.doPath(x1, y1, x2, y2);
		paver.grid = path.getGrid();
	}
	
	// draws the grid to screen;
	// showing obstructions in black,
	// and the path in red.
	public void paint(Graphics g) {
		g.setColor(Color.black);
		for (int x = 0; x < grid.length; ++x) {
			for (int y = 0; y < grid[x].length; ++y) {
				if (grid[x][y].blocked) {
					g.fillRect(x * 2, y * 2, 2, 2);
				}
			}
		}
		
		g.setColor(Color.red);
		Node temp = pathnode;
		while (temp != null) {
			g.fillRect(temp.x * 2, temp.y * 2, 2, 2);
			temp = temp.next;
		}
	}
}