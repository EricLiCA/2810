package ca.polymtl.inf2810.lab1;

import java.util.ArrayList;
import java.util.List;

public class Graph {

	private String startPoint;
	private List<Node> vertices;

	public Graph(String startPoint) {
		this.startPoint = startPoint;
		
		vertices = new ArrayList<Node>();
	}

	public void addNode(Node vertice) {
		if (!vertices.contains(vertice))
			vertices.add(vertice);
	}

	public Node getNode(String name) {
		for (Node vertice : vertices)
			if (vertice.hasName(name))
				return vertice;
		
		return null;
	}

	public Node getStartPoint() {
		return getNode(startPoint);
	}

}
