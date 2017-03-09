package ca.polymtl.inf2810.lab1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;

/**
 * The view. Handles the JFrame and JComponents.
 * 
 * @author Sébastien Chagnon (1804702)
 *
 */
public class PokemonRoute extends JFrame implements Observer {

	private static final long serialVersionUID = 1090645939200450050L;

	private JPanel pnlPrincipal = new JPanel(new BorderLayout());

	private static final String WINDOW_NAME = "PokemonRoute";

	/**
	 * Constructor
	 * 
	 * @param o
	 *            the observable model
	 */
	public PokemonRoute(Observable o) {

		// Add this to the observer list of the model
		o.addObserver(this);

		Modele model = (Modele) o;

		// Initialize the JFrame
		this.setTitle(WINDOW_NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1400, 600);

		// Add the JComponents in the Frame
		this.drawPrincipalPanel(model);
		this.add(pnlPrincipal);

		// Display
		this.setVisible(true);
	}

	/**
	 * Draws all the elements on the JFrame
	 * 
	 * @param model
	 *            the model
	 */
	private void drawPrincipalPanel(Modele model) {
		// Cleanup pnlPrincipal
		pnlPrincipal.removeAll();

		// Add the Menu
		pnlPrincipal.add(this.getMenu(model), BorderLayout.EAST);

		// Add the Arcs list
		pnlPrincipal.add(this.getArcsList(model), BorderLayout.WEST);

		// Add the Graph
		pnlPrincipal.add(getGraphSection(model), BorderLayout.CENTER);
	}

	/**
	 * Returns the completed Component which displays all the Arcs
	 * 
	 * @param model
	 *            the model
	 * @return the completed Component which displays all the Arcs
	 */
	private Component getArcsList(Modele model) {

		// New constraints instance
		GridBagConstraints gbc = new GridBagConstraints();

		// Adds a Margin
		gbc.insets = new Insets(5, 10, 5, 10);

		// Set the addComponent position to 0, 0
		gbc.gridx = gbc.gridy = 0;

		// Components should fill the available unused space in both directions
		gbc.fill = GridBagConstraints.BOTH;

		// Components should take all the remaining space in it's parent
		gbc.weighty = gbc.weightx = 1;

		// Will contain the JList with the arcs informations
		JPanel arcsList = new JPanel(new GridBagLayout());
		arcsList.setBackground(Color.WHITE);
		arcsList.setPreferredSize(new Dimension(275, 400));

		// Get the List of Arcs from the Graph
		List<Arc> edges = model.getEdges();

		// Will contain the list of Arc's descriptions
		String[] edgesNotation = new String[edges.size()];

		// for all the Arcs, add their descriptions in the description array
		for (int i = 0; i < edges.size(); i++) {
			edgesNotation[i] = String.format("%1$7s", edges.get(i).getN1().getName()) + " - "
					+ String.format("%1$-6s", edges.get(i).getN2().getName()) + "  Distance: "
					+ edges.get(i).getDistance();
		}

		// Creates a new JList with the Arc's descriptions
		JList<String> arcs = new JList<String>(edgesNotation);
		arcs.setBackground(new Color(210, 210, 210));

		// Monospaced font in the JList for allignement
		arcs.setFont(new Font("Courier", Font.PLAIN, arcs.getFont().getSize()));

		// Add a ClickEvent to the JMenu
		arcs.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Will contain all the selected Arcs
				List<Arc> selectedValues = new ArrayList<Arc>();

				// Get all the selected JList Values
				List<String> selected = arcs.getSelectedValuesList();

				// for each selected values, get it's representing Arc
				for (String s : selected) {

					// Splits the Arc description line at all the regex " "
					String[] elements = s.split(" ");
					int i = 0;
					Node n1 = null;
					Node n2 = null;

					// Get the first node in the String
					while (n1 == null) {
						n1 = model.getGraph().getNode(elements[i++]);
					}

					// Get the second Node in the String
					while (n2 == null) {
						n2 = model.getGraph().getNode(elements[i++]);
					}

					// Adds the found Arc to the selected Arcs List
					selectedValues.add(model.getGraph().getArc(n1, n2));
				}

				// Sets the selected values to the Graph (through the model)
				model.select(selectedValues);
			}
		});

		// Will contain the list to be able to scroll the list
		JScrollPane scrollPane = new JScrollPane(arcs);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Adds the ScrollPane to the Panel
		arcsList.add(scrollPane, gbc);

		return arcsList;
	}

	/**
	 * Gets the Menu component containing the buttons and the path description
	 * 
	 * @param model
	 *            the model
	 * @return the menu component to put in the JFrame
	 */
	private Component getMenu(Modele model) {
		// Instantiate the menu Panel
		JPanel menu = new JPanel(new GridBagLayout());
		menu.setBackground(Color.WHITE);

		// New Draw Constraints
		GridBagConstraints gbc = new GridBagConstraints();
		Dimension dimension;
		
		// Instantiate the menu buttons
		JButton points = new JButton("Highest amount of points");
		JButton distance = new JButton("Shortest distance");
		JButton draw = new JButton("Draw Graph");
		JButton quit = new JButton("Quit");

		// New mouse Click Event for the draw button
		draw.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					model.createGraph("data_pokemon.txt");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(new JFrame(), "File not found.", "", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		// New mouse Click Event for the points button
		points.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				// Get from the user how much points he wants to get
				String s = (String) JOptionPane.showInputDialog("How many points do you want to get?");

				try {
					// Execute the Lowest Distance Algorithm
					model.executeAlgorithmLowestDistance(Integer.parseInt(s));
				} catch (IllegalStateException ex) {
					// If the Graph is not yet generated, display an error
					JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException ex) {
					// If the amount entered is not an Integer, display an error
					JOptionPane.showMessageDialog(new JFrame(), "You must enter a integer value", "",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					// If no path is found with the specified value, display an error
					JOptionPane.showMessageDialog(new JFrame(), "There are no such paths", "",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// New mouse Click Event for the distance button
		distance.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				// Get from the user how much he wants to walk
				String s = (String) JOptionPane.showInputDialog("How much distance do you want to walk?");

				try {
					// Execute the Highest Point Algorithm
					model.executeAlgorithmHighestPoints(Integer.parseInt(s));
				} catch (IllegalStateException ex) {
					// If the Graph is not yet generated, display an error
					JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException ex) {
					// If the amount entered is not an Integer, display an error
					JOptionPane.showMessageDialog(new JFrame(), "You must enter a integer value", "",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					// If no path is found with the specified value, display an error
					JOptionPane.showMessageDialog(new JFrame(), "There are no such paths", "",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// New mouse Click Event for the quit button
		quit.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				// Closes the program
				System.exit(0);
			}
		});

		// Get the sizes of the biggest button
		int minWidth = Math.max(Math.max(points.getMinimumSize().width, distance.getMinimumSize().width),
				Math.max(draw.getMinimumSize().width, quit.getMinimumSize().width));
		int minHeight = Math.max(Math.max(points.getMinimumSize().height, distance.getMinimumSize().height),
				Math.max(draw.getMinimumSize().height, quit.getMinimumSize().height));

		// Sets the same minimum dimensions for all the buttons
		dimension = new Dimension(minWidth, minHeight);
		points.setMinimumSize(dimension);
		distance.setMinimumSize(dimension);
		draw.setMinimumSize(dimension);
		quit.setMinimumSize(dimension);

		// Sets the same preferred dimensions for all the buttons
		dimension.height *= 1.3;
		points.setPreferredSize(dimension);
		distance.setPreferredSize(dimension);
		draw.setPreferredSize(dimension);
		quit.setPreferredSize(dimension);

		// Sets the same maximum dimensions for all the buttons
		dimension.height *= 1.2;
		points.setMaximumSize(dimension);
		distance.setMaximumSize(dimension);
		draw.setMaximumSize(dimension);
		quit.setMaximumSize(dimension);

		// Column 0
		gbc.gridx = 0;
		
		// Margin
		gbc.insets = new Insets(5, 10, 5, 10);
		
		// Takes as much space horizontally as available
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Add draw button on row 0
		gbc.gridy = 0;
		menu.add(draw, gbc);

		// Add draw button on row 1
		gbc.gridy = 1;
		menu.add(distance, gbc);

		// Add draw button on row 2
		gbc.gridy = 2;
		menu.add(points, gbc);

		// Add draw button on row 3
		gbc.gridy = 3;
		menu.add(quit, gbc);

		// Row 4
		gbc.gridy = 4;

		// Takes as much space in both directions as available
		gbc.fill = GridBagConstraints.BOTH;
		
		// Takes as much space vertically possible
		gbc.weighty = 1;

		// New JList containing the Path of the Graph
		JList<String> path = new JList<String>(model.getPath());
		path.setBackground(new Color(210, 210, 210));

		// Monospaced font in the JList for allignement
		path.setFont(new Font("Courier", Font.PLAIN, path.getFont().getSize()));

		// New Mouse listener to the JList elements
		path.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Does Nothing
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Will contain the selected Arcs
				List<Arc> selectedValues = new ArrayList<Arc>();
				
				// Get all the elements of the JList
				ListModel<String> selected = path.getModel();

				Node last = null;
				Node actual = null;

				// for each of the elements in the JList
				for (int i = 0; i < selected.getSize(); i++) {
					// Save the last element
					last = actual;
					
					// Splits the JList element String by the regex " "
					String[] elements = selected.getElementAt(i).split(" ");

					int j = 0;
					actual = null;
					
					// get the Node actual Node
					while (actual == null) {
						actual = model.getGraph().getNode(elements[j++]);
					}

					// get the Arc from the current Nodde the the last one
					if (last != null)
						selectedValues.add(model.getGraph().getArc(last, actual));
				}

				// Sets the selected values to the Graph (through the model)
				model.select(selectedValues);
			}
		});

		// Will contain the list to be able to scroll the list
		JScrollPane scrollPane = new JScrollPane(path);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Sets the preferred size of the menu
		dimension.width *= 1.5;
		scrollPane.setPreferredSize(dimension);

		// Adds the scrollPane containing the JList to the menu on row 4
		menu.add(scrollPane, gbc);

		return menu;
	}

	/**
	 * Gets the Graph component containing visual representation of the graph
	 * 
	 * @param model
	 *            the model
	 * @return the graph component to put in the JFrame
	 */
	private JPanel getGraphSection(Modele model) {

		// Get the Graph from the model
		JPanel graph = model.getGraph();
		
		// If null, replace graph with a startup image
		if (graph == null) {

			// Create a New JPanel to hols the picture
			graph = new JPanel() {
				
				private static final long serialVersionUID = 3683493550838324113L;

				/**
				 * Paint the startup image to the Graphics
				 */
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);

					// Get the image in the jar and draws it to the Graphics
					InputStream is = ClassLoader.getSystemResourceAsStream("ca/polymtl/inf2810/lab1/pogo.jpg");
					Image image = null;
					try {
						image = ImageIO.read(is);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					g.drawImage(image, 0, 0, this);
				}
			};
		}

		return graph;
	}

	/**
	 * When in being notified by the model
	 */
	@Override
	public void update(Observable o, Object arg) {
		Modele model = (Modele) o;
		this.drawPrincipalPanel(model);
		this.revalidate();
		this.repaint();
	}

}
