package ca.polymtl.inf2810.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Path {

	Integer pointsGained;
	Integer distanceTraveled;
	LinkedList<Node> pastNodes;
	Map<Node, Integer> deactivated;

	public Path(Graph graph) {
		this.pastNodes = new LinkedList<Node>();
		this.deactivated = new HashMap<Node, Integer>();

		this.pointsGained = 0;
		this.distanceTraveled = 0;
		this.pastNodes.add(graph.getStartPoint());
	}

	public Path(Path path, Node newNode) {

		System.out.println("===============");
		System.out.println(path.pastNodes.getLast());
		System.out.println(newNode);
		
		this.distanceTraveled = path.pastNodes.getLast().getDistanceFrom(newNode);

		this.pastNodes = new LinkedList<Node>(path.pastNodes);
		this.pastNodes.addLast(newNode);

		this.deactivated = new HashMap<>(path.deactivated);
		List<Node> toReactivate = new ArrayList<Node>();
		for (Entry<Node, Integer> vertice : this.deactivated.entrySet()) {
			
			
			
			System.out.println(vertice.getValue());
			System.out.println(this.distanceTraveled);
			System.out.println();
			
			
			
			int updatedDistance = vertice.getValue() - this.distanceTraveled;
			if (updatedDistance <= 0)
				vertice.setValue(updatedDistance);
			else
				toReactivate.add(vertice.getKey());
		}

		for (Node vertice : toReactivate)
			this.deactivated.remove(vertice);

		this.pointsGained = this.deactivated.containsKey(newNode) ? path.pointsGained
				: path.pointsGained + newNode.getValue();

		this.deactivated.put(newNode, newNode.getDeactivationTime());
	}

	public List<Path> getSubPaths(Dijkstra dijkstra) {
		List<Path> subPaths = new ArrayList<Path>();

		for (Entry<Node, Integer> edge : pastNodes.getLast().getConnections().entrySet()) {
			System.out.println("=============================================================");
			System.out.println(this);
			System.out.println(edge.getKey());
			Path newSubPath = new Path(this, edge.getKey());

			if (dijkstra.isFinished(newSubPath))
				dijkstra.addFinishedPath(newSubPath);

			else if (dijkstra.isTooFar(newSubPath))
				dijkstra.addFinishedPath(this);

			else
				subPaths.add(newSubPath);
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
//		sb.append("Optimal path : \n" + "Distance : " + distanceTraveled + "\n");
//		sb.append("Points : " + pointsGained + "\n");
		
		Iterator<Node> it = pastNodes.iterator();
		while (it.hasNext()) {
			Node node = it.next();
//			sb.append("\t- " + String.format("%1$-20s", node) + "\n");
			sb.append(" - " + String.format("%1$-18s", node));
		}
		
		return sb.toString() ;
	}

}
