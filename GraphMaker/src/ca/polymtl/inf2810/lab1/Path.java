package ca.polymtl.inf2810.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A Path represents a route between many Nodes in a Graph via Arcs
 * 
 * @author Sébastien Chagnon (1804702)
 *
 */
public class Path implements Comparable<Path> {

	Graph graph;
	Integer pointsGained;
	Integer distanceTraveled;
	LinkedList<Node> pastNodes;
	Map<Node, Integer> deactivated;

	/**
	 * Constructor from a graph. The path is initiallised with one past Node
	 * which is the start point.
	 * 
	 * @param graph
	 *            the graph to start a path from
	 */
	public Path(Graph graph) {
		this.graph = graph;
		this.pastNodes = new LinkedList<Node>();
		this.deactivated = new HashMap<Node, Integer>();

		this.pointsGained = 0;
		this.distanceTraveled = 0;
		this.pastNodes.add(graph.getStartPoint());
	}

	/**
	 * Constructor that extends another path by adding a node to it's route
	 * 
	 * @param path
	 *            the Path to extends
	 * @param newNode
	 *            the new Node to add to the Path
	 * @param graph
	 *            the graph in which the Path is
	 */
	public Path(Path path, Node newNode, Graph graph) {

		this.graph = graph;

		// Update the distance traveled
		int lastTravel = path.pastNodes.getLast().getDistanceFrom(newNode);
		this.distanceTraveled = path.distanceTraveled + lastTravel;

		// Copy the past Nodes from the original Path and add the new Node to
		// the end of it
		this.pastNodes = new LinkedList<Node>(path.pastNodes);
		this.pastNodes.addLast(newNode);

		// Copy the deactivated Nodes from the original Path
		this.deactivated = new HashMap<>(path.deactivated);

		// A list of Nodes to remove from the disabled map
		List<Node> toReactivate = new ArrayList<Node>();

		// for each Entry in the deactivated map
		for (Entry<Node, Integer> vertice : this.deactivated.entrySet()) {

			// The new distance cooldown is calculated
			int updatedDistance = vertice.getValue() - lastTravel;

			// If the cooldown is over, add the Node to the reactivate list
			if (updatedDistance <= 0)
				toReactivate.add(vertice.getKey());

			// Else update the cooldown
			else
				vertice.setValue(updatedDistance);
		}

		// Remove all the Node which their cooldowns are over from the
		// deactivated list
		for (Node vertice : toReactivate)
			this.deactivated.remove(vertice);

		// Update the amount of points gained
		this.pointsGained = this.deactivated.containsKey(newNode) ? path.pointsGained
				: path.pointsGained + newNode.getValue();

		// Deactivate the arrival Node
		this.deactivated.put(newNode, newNode.getDeactivationTime());
	}

	/**
	 * Get all the subpaths that can be continued from this Path
	 * 
	 * @param algorithm
	 * @return a List of the subpaths
	 */
	public List<Path> getSubPaths(RatioAlgorithm algorithm) {
		// Initialize a new list that will contain the subPaths
		List<Path> subPaths = new ArrayList<Path>();

		// for each connections from the last Node of the Path to other Nodes
		for (Entry<Node, Integer> edge : pastNodes.getLast().getConnections().entrySet()) {

			// Creates a new subPath
			Path newSubPath = new Path(this, edge.getKey(), this.graph);

			if (algorithm.isFinished(newSubPath)) {

				// If the new subPath is finished, add it to the finished paths
				// of the algorithm
				algorithm.addFinishedPath(newSubPath);

			} else if (algorithm.isTooFar(newSubPath)) {

				// If the new subPath is to far, add this path to the finished
				// paths of the algoritm
				algorithm.addFinishedPath(this);

			} else {

				// Else add the new subPath to the subPath List
				subPaths.add(newSubPath);
			}
		}

		return subPaths;
	}

	/**
	 * Get the ratio distance over points of this Path. If the amount of points
	 * is 0, returns the maximum value of a Double.
	 * 
	 * @return
	 */
	public double getRatio() {
		double ratio;
		try {
			ratio = ((double) distanceTraveled) / ((double) pointsGained);
		} catch (ArithmeticException e) {
			ratio = Double.MAX_VALUE;
		}

		return ratio;
	}

	/**
	 * Returns the total amount of points from the Path
	 * 
	 * @return the total amount of points from the Path
	 */
	public int getTotalPoints() {
		return pointsGained;
	}

	/**
	 * Returns the total distance travelled on this Path
	 * 
	 * @return the total distance travelled on this Path
	 */
	public int getDistance() {
		return distanceTraveled;
	}

	/**
	 * Compare the Path based on their ratios.
	 */
	@Override
	public int compareTo(Path o) {

		double ratioDiff = this.getRatio() - o.getRatio();

		if (ratioDiff < 0)
			return -1;
		if (ratioDiff > 0)
			return 1;

		return 0;
	}

	/**
	 * To get a list of all the steps of the Path, with the corresponding points
	 * and distances
	 * 
	 * @return an Array of String that can be put in a JList to explain a Graph
	 */
	public String[] getDetailedRoute() {
		// Initialize the list with the right size
		String[] detailedRoute = new String[pastNodes.size()];

		// Iterator to iterate through the Path's Nodes
		Iterator<Node> it = pastNodes.iterator();

		// Current step number
		int i = 0;

		// Current Node
		Node node = it.next();

		// Temporary path to simulate the Path from it's beginning and get the
		// Points and Distances from every steps of the way
		Path temp = new Path(graph);

		// Add the information of the start point
		detailedRoute[i++] = String.format("%1$-8s", node.getName()) + "Distance:"
				+ String.format("%1$-6s", temp.distanceTraveled) + "Points:"
				+ String.format("%1$-6s", temp.pointsGained);

		// While iterator is not at the end of the list
		while (it.hasNext()) {
			
			// Add the current Node to the temporary Path
			node = it.next();
			temp = new Path(temp, node, graph);

			// Add the information of the current Node and the Paths Distance and Points
			detailedRoute[i++] = String.format("%1$-8s", node.getName()) + "Distance:"
					+ String.format("%1$-6s", temp.distanceTraveled) + "Points:"
					+ String.format("%1$-6s", temp.pointsGained);
		}

		return detailedRoute;
	}

	/**
	 * Returns the List of Nodes this Path passes through
	 * 
	 * @return the List of Nodes this Path passes through
	 */
	public List<Node> getVertices() {
		return pastNodes;
	}

}
