package ca.polymtl.inf2810.lab1;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GraphMaker {

	/**
	 * Creates a Graph with the passed Nodes, and optimizes it to remove the
	 * Arcs that are not necessary in the Graph because there is already a path
	 * of the appropriate length that passes through other Nodes
	 * 
	 * @param nodes
	 *            all the nodes
	 * @param edges
	 *            the shortest distance between two nodes
	 * @return a complete and optimized Graph
	 */
	public static Graph execute(Map<String, Node> nodes, List<Arc> edges) {

		// Creates a new Graph with the Nodes
		Graph graph = new Graph(nodes.values());

		// Sorts the Arcs based on their lengths
		sort(edges);

		// for each Arcs (in length order)
		for (Arc edge : edges) {

			// If there if not already a Path of the appropriate length that
			// links the two nodes
			if (!checkForPath(edge.getN1(), edge.getDistance(), edge.getN2(), nodes)) {

				// Make a link between the two nodes
				edge.getN2().setDistance(edge.getN1(), edge.getDistance());
				edge.getN1().setDistance(edge.getN2(), edge.getDistance());
				graph.addEdge(edge);
			}
		}

		return graph;

	}

	/**
	 * Check if there is a path between the current Node to the goal with the
	 * distance remaining. This method is recursive.
	 * 
	 * @param goal
	 *            the Node the algorithm tries to reach
	 * @param progression
	 *            the the current position and the distance remaining
	 * @param nodes
	 *            all the Nodes in the Graph
	 * @return true if there is already a path between the two nodes of the
	 *         required length, flase otherwise
	 */
	private static boolean checkForPath(final Node current, final int distanceRemaining, final Node goal,
			final Map<String, Node> nodes) {

		// If reached the goal, return true
		if (distanceRemaining == 0 && current == goal)
			return true;

		// If passed the goal, return false
		else if (distanceRemaining < 0) {
			return false;
		}

		// Else (if distance > 0)
		// for each Nodes in the Graph
		for (Entry<String, Node> node : nodes.entrySet()) {

			// If the n Node in the Graph is connected to the current Node
			if (current.isConnectedTo(node.getValue())) {

				// Gets the distance between the current node and the n Node
				Integer distanceToNode = current.getDistanceFrom(node.getValue());

				// If the distance is higher than 0 (because otherwise the
				// algorithm would never end (Stack Overflow))
				if (distanceToNode > 0) {

					// There is a path between the n node to the goal with the
					// remaining distance, return true
					if (checkForPath(node.getValue(), distanceRemaining - distanceToNode, goal, nodes))
						return true;
				}
			}
		}

		return false;

	}

	/**
	 * Sorts the Arcs based on their length
	 * 
	 * @param distances the Arcs to sort
	 */
	private static void sort(List<Arc> distances) {
		Collections.sort(distances, new Comparator<Arc>() {

			@Override
			public int compare(Arc o1, Arc o2) {
				return o1.getDistance() - o2.getDistance();
			}
		});
	}

}
