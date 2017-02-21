package ca.polymtl.inf2810.lab1;

public class Arc {

	Node n1;
	Node n2;
	int distance;
	
	public Arc(Node n1, Node n2, int distance) {
		this.n1 = n1;
		this.n2 = n2;
		this.distance = distance;
	}
	
	public Node getN1() {
		return n1;
	}
	
	public Node getN2() {
		return n2;
	}
	
	public int getDistance() {
		return distance;
	}
	
	@Override
	public String toString() {
		return String.format("%1$7s", n1.getName()) + " - "
				+ String.format("%1$-6s", n2.getName()) + "  Distance: "
				+ distance;
	}
	
}
