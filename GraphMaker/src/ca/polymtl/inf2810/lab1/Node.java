package ca.polymtl.inf2810.lab1;

import java.util.HashMap;
import java.util.Map;

public class Node {
	private String name;
	private NodeType type;
	private Integer value;
	private Map<Node, Integer> links;

	public Node(String name, NodeType type, Integer value) {
		this.name = name;
		this.type = type;
		this.value = value;
		
		this.links = new HashMap<Node, Integer>();
		
	}

	public void setDistance(Node node, Integer distance) {
		this.links.put(node, distance);
	}
	
	public boolean isConnectedTo(Node node) {
		return links.containsKey(node);
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
		return links.get(node);
	}

	@Override
	public String toString() {
		return name + "(" + value + ")";
	}

}
