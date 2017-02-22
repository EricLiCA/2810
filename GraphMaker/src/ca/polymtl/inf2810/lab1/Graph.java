package ca.polymtl.inf2810.lab1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

public class Graph extends JPanel {

	private static final long serialVersionUID = -3462949872752448755L;

	/**
	 * The name of the Node where the graph will start
	 */
	private static final String START_POINT = "depart";

	private List<Node> vertices;
	private List<Arc> edges;
	private List<Arc> selectedEdges;

	/**
	 * Constructor
	 * 
	 * @param vertices
	 *            a collection of Nodes that will constitute the graph
	 */
	public Graph(Collection<Node> vertices) {

		this.vertices = new ArrayList<Node>(vertices);
		this.edges = new ArrayList<Arc>();
		this.selectedEdges = new ArrayList<Arc>();
	}

	/**
	 * Adds a Node to this Graph
	 * 
	 * @param vertice
	 *            the Node to add to this Graph
	 */
	public void addNode(Node vertice) {
		if (!vertices.contains(vertice))
			vertices.add(vertice);
	}

	/**
	 * Adds an Arc to this Graph
	 * 
	 * @param edge
	 *            the Arc to add to this Graph
	 */
	public void addEdge(Arc edge) {
		if (!edges.contains(edge))
			edges.add(edge);
	}

	/**
	 * Returns the List of Arc handled by this Graph
	 * 
	 * @return the List of Arc handled by this Graph
	 */
	public List<Arc> getEdges() {
		return edges;
	}

	/**
	 * Returns the Node which it's name is passed in parameters. It the required
	 * Node is not handled by this Graph, returns null.
	 * 
	 * @param name
	 *            the name of the Node to return
	 * @return the required Node, or null if the required Node is not handled by
	 *         this Graph
	 */
	public Node getNode(String name) {
		for (Node vertice : vertices)
			if (vertice.hasName(name))
				return vertice;

		return null;
	}

	/**
	 * Returns the Node with the name defined by the private static variable
	 * START_POINT. If not found, returns null.
	 * 
	 * @return the Node where the Path starts, or null if there is no Node with
	 *         such name handled by this Graph
	 */
	public Node getStartPoint() {
		return getNode(START_POINT);
	}

	/**
	 * Returns the Arc that has the two Nodes passed in parameter at it's ends.
	 * 
	 * @param n1
	 *            first end of the Arc
	 * @param n2
	 *            second end of the Arc
	 * @return the coresponding Arc
	 */
	public Arc getArc(Node n1, Node n2) {
		for (Arc edge : edges)
			if ((edge.getN1() == n1 && edge.getN2() == n2) || (edge.getN1() == n2 && edge.getN2() == n1))
				return edge;

		return null;
	}

	/**
	 * Sets the selected values. Used when drawing the Graph
	 * 
	 * @param selectedValues
	 *            the Arcs to highlight
	 */
	public void setSelectedValues(List<Arc> selectedValues) {
		this.selectedEdges = selectedValues;
	}

	/**
	 * Paints the Graph to the Graphics component of the JLabel
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// The JLabel is in 2 dimensions
		Graphics2D g2 = (Graphics2D) g;

		// The color of the stroke
		g2.setColor(Color.BLACK);

		// Finds the center and radius of the JLabel
		// Will be used to paint the Graph
		int centerx = this.getWidth() / 2;
		int centery = this.getHeight() / 2;
		int radius = (int) (Math.min(centerx, centery) * 0.65);

		// A temporary Map to store the radial positions of the Nodes.
		// Will be used to draw the edges
		Map<Node, Double> radialPositions = new HashMap<Node, Double>();

		// For each vertices
		for (int i = 0; i < vertices.size(); i++) {
			Node toDraw = vertices.get(i);

			// Gets the Node, finds it's radial position and stores it in the
			// map containing the radial positions
			double radPos = ((2 * Math.PI) / vertices.size()) * i;
			radialPositions.put(toDraw, radPos);

			// The position of the Node on the 2D Graphics
			int pointPosX = centerx + (int) (Math.cos(radPos) * radius);
			int pointPosY = centery - (int) (Math.sin(radPos) * radius);

			// Changes the weight of the line
			g2.setStroke(new BasicStroke(7));

			// Draw a point to the Node position
			g2.drawLine(pointPosX, pointPosY, pointPosX, pointPosY);

			// Finds the center and radius of the label of the node
			int circlePosX = centerx + (int) (Math.cos(radPos) * (radius * 1.2));
			int circlePosY = centery - (int) (Math.sin(radPos) * (radius * 1.2));
			int circleRadius = (int) ((radius * 1.15) - radius) * 2;

			// Changes the font to be Bold and size 20
			g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, 20));

			// Changes the weight of the line
			g2.setStroke(new BasicStroke(2));

			// Draw the label of the Node and it's value inside
			g2.drawOval(circlePosX - circleRadius / 2, circlePosY - circleRadius / 2, circleRadius, circleRadius);
			g2.drawString(String.valueOf(toDraw.getValue()), circlePosX - circleRadius / 4,
					circlePosY + circleRadius / 8);

			// Finds the position of the name of the label
			int namePosX = centerx + (int) (Math.cos(radPos) * (radius * 1.45) - centerx * 0.04);
			int namePosY = centery - (int) (Math.sin(radPos) * (radius * 1.42) - centerx * 0.02);

			// Draw the name
			g2.drawString(toDraw.getName(), namePosX, namePosY);

		}

		// for each Arc in the Graph
		for (Arc edge : edges) {

			// Finds the position of the two end points of the Arc of the 2D
			// Graphics
			double n1radPos = radialPositions.get(edge.getN1());
			int n1PosX = centerx + (int) (Math.cos(n1radPos) * radius);
			int n1PosY = centery - (int) (Math.sin(n1radPos) * radius);
			double n2radPos = radialPositions.get(edge.getN2());
			int n2PosX = centerx + (int) (Math.cos(n2radPos) * radius);
			int n2PosY = centery - (int) (Math.sin(n2radPos) * radius);

			// If this edge should be highlighted
			if (selectedEdges.contains(edge)) {

				// The position of the current edge in all the selected ones
				int edgePos = selectedEdges.indexOf(edge);

				// To create a color gradient for the path
				// Ajusts the red amount of the line's color based on it's
				// position in the selected edges
				int red = (int) ((edgePos <= selectedEdges.size() / 2.0) ? 255
						: (double) (selectedEdges.size() - edgePos) / ((double) selectedEdges.size() / 2.0) * 255.0);
				// Ajusts the green amount for the line's color based on it's
				// position in the selected edges
				int green = (int) ((edgePos <= selectedEdges.size() / 2.0)
						? (double) edgePos / ((double) selectedEdges.size() / 2.0) * 255.0 : 255);
				// Ajusts the blue amount for the line's color
				int blue = 0;

				// Sets the Color of the stroke
				g2.setColor(new Color(red, green, blue));

			} else
				// Sets the color of the Stroke
				g2.setColor(Color.BLACK);

			// Sets the weight of the stroke based on if it's highlighted or not
			g2.setStroke(selectedEdges.contains(edge) ? new BasicStroke(3) : new BasicStroke(1));

			// Draws the Arc
			g2.drawLine(n1PosX, n1PosY, n2PosX, n2PosY);
		}
	}

}
