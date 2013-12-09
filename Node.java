/**
	Copyright Andrew Helenius, 2013

	A Node in the pathfinder.
**/

class Node{
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int f = 0;
	public int g = 0;
	public int h = 0;
	
	public int x = 0;
	public int y = 0;
	
	public Node next;
	
	public boolean blocked;
	
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		
		Node o = (Node)other;
		return (o.x == x && o.y == y);
	}
}
