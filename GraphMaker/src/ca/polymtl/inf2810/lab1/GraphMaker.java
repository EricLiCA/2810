package ca.polymtl.inf2810.lab1;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GraphMaker {

	public static Graph execute(Map<String, Node> nodes, List<Arc> edges) {
		
		Graph graph = new Graph(nodes.values());
		
		sort(edges);

		for (Arc edge : edges) {
			if (!checkForPath(edge.getN2(), new Pair<Node, Integer>(edge.getN1(), edge.getDistance()), nodes)) {
				edge.getN2().setDistance(edge.getN1(), edge.getDistance());
				edge.getN1().setDistance(edge.getN2(), edge.getDistance());
				graph.addEdge(edge);
			}
		}

		return graph;
		
	}

	private static boolean checkForPath(final Node goal, final Pair<Node, Integer> progression,
			final Map<String, Node> nodes) {
		if (progression.getValue() == 0 && progression.getKey().equals(goal))
			return true;
		else if (progression.getValue() < 0) {
			return false;
		}

		for (Entry<String, Node> node : nodes.entrySet()) {
			if (progression.getKey().isConnectedTo(node.getValue())) {
				Integer distanceToNode = progression.getKey().getDistanceFrom(node.getValue());
				if (distanceToNode > 0) {
					Pair<Node, Integer> newProgression = new Pair<Node, Integer>(node.getValue(),
							progression.getValue() - distanceToNode);
					if (checkForPath(goal, newProgression, nodes))
						return true;
				}
			}
		}
		return false;

	}

	private static void sort(List<Arc> distances) {
		Collections.sort(distances, new Comparator<Arc>() {

			@Override
			public int compare(Arc o1, Arc o2) {
				return o1.getDistance() - o2.getDistance();
			}
		});
	}

}
