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
import java.util.Map.Entry;

import javax.swing.JPanel;

public class Graph extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3462949872752448755L;

	private static final String START_POINT = "depart";

	private List<Node> vertices;
	private List<Arc> edges;
	private List<Arc> selectedEdges;

	public Graph(Collection<Node> vertices) {

		this.vertices = new ArrayList<Node>(vertices);
		this.edges = new ArrayList<Arc>();
		this.selectedEdges = new ArrayList<Arc>();
	}

	public void addNode(Node vertice) {
		if (!vertices.contains(vertice))
			vertices.add(vertice);
	}

	public void addEdge(Arc edge) {
		if (!edges.contains(edge))
			edges.add(edge);
	}

	public List<Arc> getEdges() {
		return edges;
	}

	public Node getNode(String name) {
		for (Node vertice : vertices)
			if (vertice.hasName(name))
				return vertice;

		return null;
	}

	public Node getStartPoint() {
		return getNode(START_POINT);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);

		int centerx = this.getWidth() / 2;
		int centery = this.getHeight() / 2;

		int radius = (int) (Math.min(centerx, centery) * 0.65);

		Map<Node, Double> radialPositions = new HashMap<Node, Double>();
		for (int i = 0; i < vertices.size(); i++) {
			Node toDraw = vertices.get(i);
			double radPos = ((2 * Math.PI) / vertices.size()) * i;

			radialPositions.put(toDraw, radPos);

			int pointPosX = centerx + (int) (Math.cos(radPos) * radius);
			int pointPosY = centery - (int) (Math.sin(radPos) * radius);

			g2.setStroke(new BasicStroke(7));
			g2.drawLine(pointPosX, pointPosY, pointPosX, pointPosY);

			int circlePosX = centerx + (int) (Math.cos(radPos) * (radius * 1.2));
			int circlePosY = centery - (int) (Math.sin(radPos) * (radius * 1.2));
			int circleRadius = (int) ((radius * 1.15) - radius) * 2;

			g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, 20));
			g2.setStroke(new BasicStroke(2));
			g2.drawOval(circlePosX - circleRadius / 2, circlePosY - circleRadius / 2, circleRadius, circleRadius);
			g2.drawString(String.valueOf(toDraw.getValue()), circlePosX - circleRadius / 4,
					circlePosY + circleRadius / 8);

			int namePosX = centerx + (int) (Math.cos(radPos) * (radius * 1.45) - centerx * 0.04);
			int namePosY = centery - (int) (Math.sin(radPos) * (radius * 1.42) - centerx * 0.02);
			g2.drawString(toDraw.getName(), namePosX, namePosY);

			g2.setStroke(new BasicStroke(1));
			for (Entry<Node, Integer> connection : toDraw.getConnections().entrySet())
				if (radialPositions.containsKey(connection.getKey())) {
					double linkedNodeRadPos = radialPositions.get(connection.getKey());
					int linkedNodePointPosX = centerx + (int) (Math.cos(linkedNodeRadPos) * radius);
					int linkedNodePointPosY = centery - (int) (Math.sin(linkedNodeRadPos) * radius);

					g2.drawLine(pointPosX, pointPosY, linkedNodePointPosX, linkedNodePointPosY);

				}

		}

		for (Arc edge : edges) {
			double n1radPos = radialPositions.get(edge.n1);
			int n1PosX = centerx + (int) (Math.cos(n1radPos) * radius);
			int n1PosY = centery - (int) (Math.sin(n1radPos) * radius);
			double n2radPos = radialPositions.get(edge.n2);
			int n2PosX = centerx + (int) (Math.cos(n2radPos) * radius);
			int n2PosY = centery - (int) (Math.sin(n2radPos) * radius);

			if (selectedEdges.contains(edge)) {
				int edgePos = selectedEdges.indexOf(edge);

				int red = (int) ((edgePos <= selectedEdges.size() / 2.0) ? 255
						: (double) (selectedEdges.size() - edgePos) / ((double) selectedEdges.size() / 2.0) * 255.0);
				int green = (int) ((edgePos <= selectedEdges.size() / 2.0)
						? (double) edgePos / ((double) selectedEdges.size() / 2.0) * 255.0 : 255);
				int blue = 0;

				g2.setColor(new Color(red, green, blue));

			} else
				g2.setColor(Color.BLACK);
			g2.setStroke(selectedEdges.contains(edge) ? new BasicStroke(3) : new BasicStroke(1));

			g2.drawLine(n1PosX, n1PosY, n2PosX, n2PosY);
		}

		g2.setStroke(new BasicStroke(10));

	}

	public Arc getArc(Node n1, Node n2) {
		for (Arc edge : edges)
			if ((edge.n1 == n1 && edge.n2 == n2) || (edge.n1 == n2 && edge.n2 == n1))
				return edge;

		return null;
	}

	public void setSelectedValues(List<Arc> selectedValues) {
		this.selectedEdges = selectedValues;
	}

}
