package ca.polymtl.inf2810.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Path implements Comparable<Path> {

	Graph graph;
	Integer pointsGained;
	Integer distanceTraveled;
	LinkedList<Node> pastNodes;
	Map<Node, Integer> deactivated;

	public Path(Graph graph) {
		this.graph = graph;
		this.pastNodes = new LinkedList<Node>();
		this.deactivated = new HashMap<Node, Integer>();

		this.pointsGained = 0;
		this.distanceTraveled = 0;
		this.pastNodes.add(graph.getStartPoint());
	}

	public Path(Path path, Node newNode, Graph graph) {

		this.graph = graph;

		int lastTravel = path.pastNodes.getLast().getDistanceFrom(newNode);
		this.distanceTraveled = path.distanceTraveled + lastTravel;

		this.pastNodes = new LinkedList<Node>(path.pastNodes);
		this.pastNodes.addLast(newNode);

		this.deactivated = new HashMap<>(path.deactivated);
		List<Node> toReactivate = new ArrayList<Node>();
		for (Entry<Node, Integer> vertice : this.deactivated.entrySet()) {

			int updatedDistance = vertice.getValue() - lastTravel;
			if (updatedDistance <= 0)
				toReactivate.add(vertice.getKey());
			else
				vertice.setValue(updatedDistance);
		}

		for (Node vertice : toReactivate)
			this.deactivated.remove(vertice);

		this.pointsGained = this.deactivated.containsKey(newNode) ? path.pointsGained
				: path.pointsGained + newNode.getValue();

		this.deactivated.put(newNode, newNode.getDeactivationTime());
	}

	public List<Path> getSubPaths(RatioAlgorithm dijkstra) {
		List<Path> subPaths = new ArrayList<Path>();

		for (Entry<Node, Integer> edge : pastNodes.getLast().getConnections().entrySet()) {
			Path newSubPath = new Path(this, edge.getKey(), this.graph);

			if (dijkstra.isFinished(newSubPath)) {
				dijkstra.addFinishedPath(newSubPath);
			} else if (dijkstra.isTooFar(newSubPath)) {
				dijkstra.addFinishedPath(this);
			} else {
				subPaths.add(newSubPath);
			}
		}

		return subPaths;
	}

	public double getRatio() {
		double ratio;
		try {
			ratio = ((double) distanceTraveled) / ((double) pointsGained);
		} catch (ArithmeticException e) {
			ratio = Double.MAX_VALUE;
		}

		return ratio;
	}

	public int getTotalPoints() {
		return pointsGained;
	}

	public int getDistance() {
		return distanceTraveled;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Iterator<Node> it = pastNodes.iterator();
		int distance = 0;
		int points = 0;

		Node node = it.next();
		sb.append("\n - " + String.format("%1$-18s", node));
		while (it.hasNext()) {
			Node previousNode = node;
			node = it.next();
			sb.append("\n - " + String.format("%1$-18s", node));

			distance += previousNode.getDistanceFrom(node);
			points += node.getValue();

			sb.append("Ratio = " + distance + "/" + points + " = " + ((double) distance / points));

		}

		return sb.toString();
	}

	@Override
	public int compareTo(Path o) {

		double ratioDiff = this.getRatio() - o.getRatio();

		if (ratioDiff < 0)
			return -1;
		if (ratioDiff > 0)
			return 1;

		return 0;
	}

	public String[] getDetailedRoute() {
		String[] detailedRoute = new String[pastNodes.size()];

		Iterator<Node> it = pastNodes.iterator();
		int i = 0;
		Node node = it.next();
		Path temp = new Path(graph);

		detailedRoute[i++] = String.format("%1$-8s", node.getName()) + "Distance:"
				+ String.format("%1$-6s", temp.distanceTraveled) + "Points:" + String.format("%1$-6s", temp.pointsGained);

		while (it.hasNext()) {
			node = it.next();

			temp = new Path(temp, node, graph);

			detailedRoute[i++] = String.format("%1$-8s", node.getName()) + "Distance:"
					+ String.format("%1$-6s", temp.distanceTraveled) + "Points:" + String.format("%1$-6s", temp.pointsGained);
		}

		return detailedRoute;
	}

	public List<Node> getVertices() {
		return pastNodes;
	}

}
