package ca.polymtl.inf2810.lab1;

import java.util.HashMap;
import java.util.Map;

public class Node {
	private String name;
	private NodeType type;
	private Integer value;
	private Map<Node, Integer> edges;

	public Node(String name, NodeType type, Integer value) {
		this.name = name;
		this.type = type;
		this.value = value;
		
		this.edges = new HashMap<Node, Integer>();
		
	}

	public void setDistance(Node node, Integer distance) {
		this.edges.put(node, distance);
	}
	
	public boolean isConnectedTo(Node node) {
		return edges.containsKey(node);
	}

	public Integer getValue() {
		return value;
	}

	public NodeType getType() {
		return type;
	}

	public enum NodeType {
		RIEN, POKEMON, POKESTOP, ARENE
	}

	public Integer getDistanceFrom(Node node) {
		return edges.get(node);
	}

	@Override
	public String toString() {
		return type.name() + " " + name + "(" + value + ")";
	}

	public Map<Node, Integer> getConnections() {
		return edges;
	}

	public boolean hasName(String name) {
		return this.name.equals(name);
	}

	public Integer getDeactivationTime() {
		switch (type) {
		case ARENE:
			return Integer.MAX_VALUE;
		case POKEMON:
			switch (value) {
			case 10:
				return 100;
			case 40:
				return 200;
			case 100:
				return 500;
			default:
				return 0;
			}
		case POKESTOP:
			return 100;
		default:
			return 0;
		}
	}

}
