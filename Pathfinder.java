/**
	Copyright Andrew Helenius, 2013
	
	This is the full-fledged A* pathfinder, which can be used in games,
	solving puzzles, or finding shortest paths in general.
**/

import java.util.*;

public class Pathfinder {
	int width, height;
	Node[][] grid;
	boolean[][] closed;
	ArrayList<Node> open = new ArrayList<Node>();
	
	// constructs an obstruction grid; what we pave around //
	public void init(int width, int height) {
		this.width = width;
		this.height = height;
		
		grid = new Node[width][height];
		closed = new boolean[width][height];
		
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				grid[x][y] = new Node(x, y);
			}
		}
	}
	
	public void clear() {
		for (int i = 0; i < grid.length; ++i) {
			for (int j = 0; j < grid[i].length; ++j) {
				grid[i][j].blocked = false;
			}
		}
	}
	
	// creates an obstruction at point (x, y) //
	public void block(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return;
		grid[x][y].blocked = true;
	}

	// removes an obstruction at point (x, y) //
	public void unblock(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return;
		grid[x][y].blocked = false;
	}
	
	// returns a linear linked list of nodes in the path.
	public Node doPath(int x1, int y1, int x2, int y2) {
		open.clear();
		for (int i = 0; i < closed.length; ++i)
			for (int j = 0; j < closed[i].length; ++j)
				closed[i][j] = false;
		
		Node current = null, temp = null;
		
		current = grid[x1][y1];
		open.add(current);
		
		while (current.x != x2 || current.y != y2) {
			int w = current.x + 2;
			int h = current.y + 2;
			
			for (int x = current.x - 1; x < w; ++x) {
				if (x < 0 || x == width) continue;
				
				for (int y = current.y - 1; y < h; ++y) {
					if (y < 0 || y == height) continue;
					
					if (closed[x][y]) continue;
					
					temp = grid[x][y];
					if (temp.blocked) continue;
					
					if (!open.contains(temp)) {
						if (x == current.x || y == current.y)
							temp.g = current.g + 10;
						else
							temp.g = current.g + 14;
						temp.h = 10*(Math.abs(temp.x - x2) + Math.abs(temp.y - y2));
						temp.next = current;
						temp.f = temp.g + temp.h;
						open.add(temp);
					}
					else if (current.g < temp.g) {
						if (x == current.x || y == current.y)
							temp.g = current.g + 10;
						else
							temp.g = current.g + 14;
						temp.next = current;
						temp.f = temp.g + temp.h;
					}
				}
			}
			if (open.size() == 0) return null;
			Collections.sort(open, new Comparator<Node>() {
				public int compare(Node a, Node b) {
					if (a.f < b.f)
						return -1;
					else if (a.f > b.f)
						return 1;
					else
						return 0;
				}
			});
			current = open.get(0);
			open.remove(0);
			closed[current.x][current.y] = true;
		}
		
		return current;
	}
	
	public Node[][] getGrid() {
		return grid;
	}
}