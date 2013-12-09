/**
	Copyright Andrew Helenius, 2013

	This uses a randomized Prim's algorithm
	to construct an interesting maze. This is
	the intellectual obstacle to try and solve
	with an AI technique; namely pathfinding.
**/

import java.util.*;

class Maze {
	class Point {
		public int x, y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	int width, height;
	double complexity, density;
	
	public Maze(int width, int height, double complexity, double density) {
		this.width = (width / 2) * 2 + 1;
		this.height = (height / 2) * 2 + 1;
		this.complexity = complexity;
		this.density = density;
	}
	
	// this uses prim's algorithm with randomization to generate interesting mazes
	public boolean[][] generate() {
		int c = (int)(complexity * 5 * (width + height));
		int d = (int)(density * (width / 2) * (height / 2));
		boolean[][] grid = new boolean[width][height];
		
		for (int i = 0; i < width; ++i) {
			grid[i][0] = true;
			grid[i][height-1] = true;
		}

		for (int i = 0; i < height; ++i) {
			grid[0][i] = true;
			grid[width-1][i] = true;
		}
		
		Random r = new Random();
		ArrayList<Point> neighbors = new ArrayList<Point>(4);
		
		System.out.print("Making map ");
		for (int i = 0; i < d; ++i) {
			int x = r.nextInt(width / 2) * 2;
			int y = r.nextInt(height / 2) * 2;
			grid[x][y] = true;
			if (i % 1000 == 0) System.out.print(".");
			
			for (int j = 0; j < c; ++j) {
				neighbors.clear();
				if (x > 1) neighbors.add(new Point(x - 2, y));
				if (x < width - 2) neighbors.add(new Point(x + 2, y));
				if (y > 1) neighbors.add(new Point(x, y - 2));
				if (x < height - 2) neighbors.add(new Point(x, y + 2));
				if (neighbors.size() > 0) {
					int idx = r.nextInt(neighbors.size());
					int ny = neighbors.get(idx).y;
					int nx = neighbors.get(idx).x;
					if (!grid[nx][ny]) {
						grid[nx][ny] = true;
						grid[nx + (x - nx) / 2][ny + (y - ny) / 2] = true;
						x = nx;
						y = ny;
					}
				}
			}
		}
		System.out.println(" done!");
		
		return grid;
	}
}