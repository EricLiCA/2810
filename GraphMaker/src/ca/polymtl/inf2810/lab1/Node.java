package ca.polymtl.inf2810.lab1;

import java.util.HashMap;
import java.util.Map;

/**
 * A Node represents a vertice in a Graph. It has a name, a value and a type. It
 * can be linked to other Nodes in the Graph via Arcs.
 * 
 * @author Sébastien Chagnon (1804702)
 *
 */
public class Node {
	private String name;
	private NodeType type;
	private Integer value;
	private Map<Node, Integer> edges;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            the name of the Node
	 * @param type
	 *            the type of the Node
	 * @param value
	 *            the value of the Node
	 */
	public Node(String name, NodeType type, Integer value) {
		this.name = name;
		this.type = type;
		this.value = value;

		this.edges = new HashMap<Node, Integer>();

	}

	/**
	 * Sets the distance between this Node and another one.
	 * 
	 * @param node
	 *            the Node to create a edge to
	 * @param distance
	 *            the distance of the edge
	 */
	public void setDistance(Node node, Integer distance) {
		this.edges.put(node, distance);
	}

	/**
	 * Checks if this Node is directly connected to another Node
	 * 
	 * @param node
	 *            the Node to check the connection to
	 * @return true if they are connected, false otherwise
	 */
	public boolean isConnectedTo(Node node) {
		return edges.containsKey(node);
	}

	/**
	 * Returns the value of the Node
	 * 
	 * @return the value of the Node
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Returns the type of the Node
	 * 
	 * @return the type of the Node
	 */
	public NodeType getType() {
		return type;
	}

	/**
	 * Returns the distance between this Node and another one
	 * 
	 * @param node
	 *            the other Node
	 * @return the distance between the two nodes
	 */
	public Integer getDistanceFrom(Node node) {
		return edges.get(node);
	}

	/**
	 * Returns a map of Nodes and Integer representing the edges between this
	 * Node and the other Nodes
	 * 
	 * @return a map of Nodes and Integer
	 */
	public Map<Node, Integer> getConnections() {
		return edges;
	}

	/**
	 * Checks if this name equals the String passed in parameter.
	 * 
	 * @param name
	 *            the name to check
	 * @return true if it's the same name, false otherwise
	 */
	public boolean hasName(String name) {
		return this.name.equals(name);
	}

	/**
	 * Returns the deactivation time of the Node, based on it's type and it's
	 * value it it's a Pokemon.
	 * 
	 * @return the deactivation time
	 */
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

	/**
	 * Returns the name of the Node
	 * 
	 * @return the name of the Node
	 */
	public String getName() {
		return name;
	}

	/**
	 * The possible types of Nodes
	 * 
	 * @author Sébastien Chagnon (1804702)
	 *
	 */
	public enum NodeType {
		RIEN, POKEMON, POKESTOP, ARENE
	}

}
