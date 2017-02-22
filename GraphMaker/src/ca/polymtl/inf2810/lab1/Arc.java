package ca.polymtl.inf2810.lab1;

/**
 * An arc that links two nodes
 * 
 * @author Sébastien Chagnon (1804702)
 *
 */
public class Arc {

	private Node n1;
	private Node n2;
	private int distance;

	/**
	 * Constructor
	 * 
	 * @param n1
	 *            the node at the first end of the Arc
	 * @param n2
	 *            the node at the second end of the Arc
	 * @param distance
	 *            the length of the Arc
	 */
	public Arc(Node n1, Node n2, int distance) {
		this.n1 = n1;
		this.n2 = n2;
		this.distance = distance;
	}

	/**
	 * Returns the node at the first end of the Arc
	 * 
	 * @return the node at the first end of the Arc
	 */
	public Node getN1() {
		return n1;
	}

	/**
	 * Returns the node at the second end of the Arc
	 * 
	 * @return the node at the second end of the Arc
	 */
	public Node getN2() {
		return n2;
	}

	/**
	 * Returns the length of the Arc
	 * 
	 * @return the length of the Arc
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Used for the GUI. Returns a text representation of the Arc.
	 */
	@Override
	public String toString() {
		return String.format("%1$7s", n1.getName()) + " - " + String.format("%1$-6s", n2.getName()) + "  Distance: "
				+ distance;
	}

}
