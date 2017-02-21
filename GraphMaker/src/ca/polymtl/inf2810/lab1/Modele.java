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

public class Modele extends Observable {

	private Graph graph;
	private Path path;
	
	
	public Modele() {
//		this.state = ProgramState.STARTUP;
	}
	
	public enum ProgramState {
		STARTUP, GRAPH, SEARCH
	}

	public Graph getGraph() {
		return graph;
	}

	public String[] getPath() {
		if (path == null)
			return new String[]{};
		
		return path.getDetailedRoute();
	}
	
	public void executeAlgorithmLowestDistance(int minAmountPoints) throws IllegalStateException {
		if (graph == null)
			throw new IllegalStateException("You must create the graph before doing this!");
		
		LowestDistance ld = new LowestDistance(graph, minAmountPoints);
		this.path = ld.execute();
		
		List<Arc> selected = new ArrayList<Arc>();
		for (int i = 1; i < path.getVertices().size(); i++)
			selected.add(graph.getArc(path.getVertices().get(i-1), path.getVertices().get(i)));
		
		graph.setSelectedValues(selected);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void executeAlgorithmHighestPoints(int maxDistance) throws IllegalStateException {
		if (graph == null)
			throw new IllegalStateException("You must create the graph before doing this!");
		
		HighestScore ld = new HighestScore(graph, maxDistance);
		this.path = ld.execute();
		
		List<Arc> selected = new ArrayList<Arc>();
		for (int i = 1; i < path.getVertices().size(); i++)
			selected.add(graph.getArc(path.getVertices().get(i-1), path.getVertices().get(i)));
		
		graph.setSelectedValues(selected);
		
		this.setChanged();
		this.notifyObservers();
	}

	public List<Arc> getEdges() {
		if (graph == null)
			return new ArrayList<Arc>();
			
		return graph.getEdges();
	}

	public void createGraph() {
		
	}

	public void createGraph(String fileName) throws FileNotFoundException {
		File folder = new File(".");
		File file = new File(folder, fileName);
		createGraph(file);
	}

	public void createGraph(File file) throws FileNotFoundException {

		Map<String, Node> nodes = new HashMap<String, Node>();
		List<Arc> distances = new ArrayList<Arc>();

		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(file));
			
			String fileNodes = br.readLine();
			String[] fileNode = fileNodes.split(";");

			for (String nodeValues : fileNode) {
				String[] nodeValue = nodeValues.split(",");

				if (nodeValue.length < 3)
					continue;

				Integer nodeGain = Integer.parseInt(nodeValue[2]);
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

				nodes.put(nodeValue[0], new Node(nodeValue[0], type, nodeGain));
			}

			String distancesInformations = br.readLine();
			String[] distanceInformations = distancesInformations.split(";");
			for (String distanceInformation : distanceInformations) {
				String[] information = distanceInformation.split(",");

				if (information.length < 3)
					continue;

				Integer distance = Integer.parseInt(information[2]);

				distances.add(new Arc(nodes.get(information[0]), nodes.get(information[1]), distance));

			}

		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();

		} catch (NumberFormatException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		graph = GraphMaker.execute(nodes, distances);
		
		this.setChanged();
		this.notifyObservers();
	}

	public void select(List<Arc> selectedValues) {
		graph.setSelectedValues(selectedValues);
		this.setChanged();
		this.notifyObservers();
	}
	
	
}
