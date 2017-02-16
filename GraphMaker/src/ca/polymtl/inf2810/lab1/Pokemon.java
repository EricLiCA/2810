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

import ca.polymtl.inf2810.lab1.Node.NodeType;

public class Pokemon {

	public static final String FILE_NAME = "data_pokemon.txt";

	public static void main(String[] args) {
		File folder = new File(".");
		File file = new File(folder, FILE_NAME);

		Map<String, Node> nodes = new HashMap<String, Node>();
		List<Tuple<Integer, Node, Node>> distances = new ArrayList<Tuple<Integer, Node, Node>>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

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
					throw new Exception();
				}

				System.out.println(nodeValue[0] + " : " + type.toString().toLowerCase() + " " + nodeGain);
				nodes.put(nodeValue[0], new Node(nodeValue[0], type, nodeGain));
			}

			System.out.println();
			
			String distancesInformations = br.readLine();
			String[] distanceInformations = distancesInformations.split(";");
			for (String distanceInformation : distanceInformations) {
				String[] information = distanceInformation.split(",");

				if (information.length < 3)
					continue;

				Integer distance = Integer.parseInt(information[2]);

				System.out.println(distance + " = " + information[0] + " - " + information[1]);

				distances.add(new Tuple<Integer, Node, Node>(distance, nodes.get(information[0]), nodes.get(information[1])));

			}

		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();

		} catch (NumberFormatException e) {
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Dijkstra.execute(nodes, distances);
	}

}
