package ca.polymtl.inf2810.lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import ca.polymtl.inf2810.lab1.Node.NodeType;

/**
 * The model that controls the program and it's states
 * 
 * @author Sébastien Chagnon (1804702)
 *
 */
public class Modele extends Observable {

	private Graph graph;
	private Path path;

	/**
	 * Returns the graph handled by the model
	 * 
	 * @return the graph handled by the model
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * Returns an array of String whose each elements describe a Node in the
	 * Path with informations of the Path at the Node's position
	 * 
	 * @return an array of String that can be displayed in a JList
	 */
	public String[] getPath() {
		if (path == null)
			return new String[] {};

		return path.getDetailedRoute();
	}

	/**
	 * Executes the LowestDistance variance of the RatioAlgorithm to this graph
	 * and sets this path to the optimized Path.
	 * 
	 * @param minAmountPoints
	 *            the amount of points required
	 * @throws IllegalStateException
	 *             when the graph has not yet been created
	 */
	public void executeAlgorithmLowestDistance(int minAmountPoints) throws IllegalStateException {

		// If the graph has not been created, throws an error
		if (graph == null)
			throw new IllegalStateException("You must create the graph before doing this!");

		// Creates the algorithm and executes it. Puts the return value in this
		// path.
		LowestDistance ld = new LowestDistance(graph, minAmountPoints);
		this.path = ld.execute();

		// Sets the selected values of the Graph to the Arcs of the Path
		// previously found
		List<Arc> selected = new ArrayList<Arc>();
		for (int i = 1; i < path.getVertices().size(); i++)
			selected.add(graph.getArc(path.getVertices().get(i - 1), path.getVertices().get(i)));
		graph.setSelectedValues(selected);

		// Notifies the Observer a change has been made
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Executes the HighestScore variance of the RatioAlgorithm to this graph
	 * and sets this path to the optimized Path.
	 * 
	 * @param maxDistance
	 *            the maximum distance to walk
	 * @throws IllegalStateException
	 *             when the graph has not yet been created
	 */
	public void executeAlgorithmHighestPoints(int maxDistance) throws IllegalStateException {

		// If the graph has not been created, throws an error
		if (graph == null)
			throw new IllegalStateException("You must create the graph before doing this!");

		// Creates the algorithm and executes it. Puts the return value in this
		// path.
		HighestScore ld = new HighestScore(graph, maxDistance);
		this.path = ld.execute();

		// Sets the selected values of the Graph to the Arcs of the Path
		// previously found
		List<Arc> selected = new ArrayList<Arc>();
		for (int i = 1; i < path.getVertices().size(); i++)
			selected.add(graph.getArc(path.getVertices().get(i - 1), path.getVertices().get(i)));
		graph.setSelectedValues(selected);

		// Notifies the Observer a change has been made
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Returns a list of Arcs that can be displayed in a JList, null if the
	 * Graph has not yet been created
	 * 
	 * @return a list of Arcs that can be displayed in a JList
	 */
	public List<Arc> getEdges() {
		if (graph == null)
			return new ArrayList<Arc>();

		return graph.getEdges();
	}

	/**
	 * Creates a graph from a fileName
	 * 
	 * @param fileName
	 *            the name of the file
	 * @throws FileNotFoundException
	 *             if the file is not found in the same directory as the
	 *             executable
	 */
	public void createGraph(String fileName) throws FileNotFoundException {
		// Get the file
		File folder = new File(".");
		File file = new File(folder, fileName);

		// Creates a map of nodes to pass to the GraphMaker execute command once
		// filled
		Map<String, Node> nodes = new HashMap<String, Node>();

		// Creates a list of Arcs to pass to the GraphMaker execute command once
		// filled
		List<Arc> distances = new ArrayList<Arc>();

		// A BufferedReader reads files
		BufferedReader br = null;
		try {

			// Instantiate a BufferedReader from the file previously found
			br = new BufferedReader(new FileReader(file));

			// Reads the Node description line of the file
			String fileNodes = br.readLine();

			// Splits it every time the regex ";" is found
			String[] fileNode = fileNodes.split(";");

			// for each node description in the file
			for (String nodeValues : fileNode) {
				// Splits it every time the regex "," is found
				String[] nodeValue = nodeValues.split(",");

				// If is not an acceptable node description, continue the loop
				if (nodeValue.length < 3)
					continue;

				// Gets the value of the Node
				Integer nodeGain = Integer.parseInt(nodeValue[2]);

				// Gets the type of the Node
				NodeType type;
				switch (nodeValue[1]) {
				case "rien":
					type = NodeType.RIEN;
					break;
				case "pokemon":
					type = NodeType.POKEMON;
					break;
				case "pokestop":
					type = NodeType.POKESTOP;
					break;
				case "arene":
					type = NodeType.ARENE;
					break;
				default:
					type = null;
				}

				// Puts the new Node in the Node map
				nodes.put(nodeValue[0], new Node(nodeValue[0], type, nodeGain));
			}

			// Reads the second line of the file that contains the Arcs
			// informations
			String distancesInformations = br.readLine();

			// Splits into indivitual arcs description
			String[] distanceInformations = distancesInformations.split(";");

			// for each individual description
			for (String distanceInformation : distanceInformations) {

				// Splits it into variables
				String[] information = distanceInformation.split(",");

				// If is not a complete description, continue loop
				if (information.length < 3)
					continue;

				// Gets the distance between the two nodes
				Integer distance = Integer.parseInt(information[2]);

				// Adds the new Arc to the List
				distances.add(new Arc(nodes.get(information[0]), nodes.get(information[1]), distance));

			}

		} catch (ArrayIndexOutOfBoundsException e) {
			// Catch if there is a problem in the infos in the file and we try
			// to access an array out of bounds
			e.printStackTrace();

		} catch (NumberFormatException e) {
			// Catch if there is a problem in the infos in the file and the
			// "supposed to be" integer cannot be cast to an Integer
			e.printStackTrace();

		} catch (IOException e) {
			// Catch if there is an error while reading or accesing the file
			e.printStackTrace();

		} finally {
			// Closes the BufferedReader
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		// Creates the Graph
		graph = GraphMaker.execute(nodes, distances);

		// Notify the Observer of changes
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Select a List of Arcs to be highlighted in the visual representation of
	 * the Graph
	 * 
	 * @param selectedValues
	 *            the Arcs to be highlighted
	 */
	public void select(List<Arc> selectedValues) {
		graph.setSelectedValues(selectedValues);

		// Notify the Observer of changes
		this.setChanged();
		this.notifyObservers();
	}

}
