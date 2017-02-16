package ca.polymtl.inf2810.lab1;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Dijkstra {

	public static int amountCheck = 0;

	public static void execute(Map<String, Node> nodes, List<Tuple<Integer, Node, Node>> distances) {
		sort(distances);

		for (Tuple<Integer, Node, Node> tuple : distances) {
			System.out.println(tuple.getKey() + "\t" + tuple.getFirst() + " - " + tuple.getSecond());
			if (!checkForPath(tuple.getSecond(), new Pair<Node, Integer>(tuple.getFirst(), tuple.getKey()), nodes)) {
				tuple.getSecond().setDistance(tuple.getFirst(), tuple.getKey());
				tuple.getFirst().setDistance(tuple.getSecond(), tuple.getKey());
				System.out.println("Link!");
			}
		}

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

	private static <T extends Tuple<Integer, Node, Node>> void sort(List<T> distances) {
		Collections.sort(distances, new Comparator<T>() {

			@Override
			public int compare(T first, T second) {
				return first.getKey() - second.getKey();
			}
		});
	}

}
