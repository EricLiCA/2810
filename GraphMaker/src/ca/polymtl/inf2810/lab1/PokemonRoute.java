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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.IllegalArgumentException;
import java.net.URI;
import java.net.URISyntaxException;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PokemonRoute extends JFrame implements Observer {

	private JPanel pnlPrincipal = new JPanel(new BorderLayout());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1090645939200450050L;
	private static final String WINDOW_NAME = "PokemonRoute";

	public PokemonRoute(Observable o) {
		o.addObserver(this);

		Modele model = (Modele) o;

		this.setTitle(WINDOW_NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);
		this.setSize(1400, 600);

		this.drawPrincipalPanel(model);
		this.add(pnlPrincipal);

		this.setVisible(true);
	}

	private void drawPrincipalPanel(Modele model) {
		pnlPrincipal.removeAll();

		pnlPrincipal.add(this.getMenu(model), BorderLayout.EAST);
		pnlPrincipal.add(this.getArcsList(model), BorderLayout.WEST);
		pnlPrincipal.add(getGraphSection(model), BorderLayout.CENTER);
	}

	private Component getArcsList(Modele model) {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.gridx = gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.weightx = 1;

		JPanel arcsList = new JPanel(new GridBagLayout());

		arcsList.setBackground(Color.WHITE);
		arcsList.setPreferredSize(new Dimension(275, 400));

		List<Arc> edges = model.getEdges();
		String[] edgesNotation = new String[edges.size()];
		for (int i = 0; i < edges.size(); i++) {
			edgesNotation[i] = String.format("%1$7s", edges.get(i).getN1().getName()) + " - "
					+ String.format("%1$-6s", edges.get(i).getN2().getName()) + "  Distance: "
					+ edges.get(i).getDistance();
		}

		JList<String> arcs = new JList<String>(edgesNotation);

		arcs.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				List<Arc> selectedValues = new ArrayList<Arc>();
				List<String> selected = arcs.getSelectedValuesList();

				for (String s : selected) {
					String[] elements = s.split(" ");
					int i = 0;
					Node n1 = null;
					Node n2 = null;

					while (n1 == null) {
						n1 = model.getGraph().getNode(elements[i++]);
					}

					while (n2 == null) {
						n2 = model.getGraph().getNode(elements[i++]);
					}

					selectedValues.add(model.getGraph().getArc(n1, n2));
				}

				model.select(selectedValues);
			}
		});

		arcs.setFont(new Font("Courier", Font.PLAIN, arcs.getFont().getSize()));
		arcs.setBackground(new Color(210, 210, 210));
		JScrollPane scrollPane = new JScrollPane(arcs);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		gbc.weightx = 1;

		arcsList.add(scrollPane, gbc);

		return arcsList;
	}

	private Component getMenu(Modele model) {
		JPanel menu = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		Dimension dimension;

		menu.setBackground(Color.WHITE);
		JButton points = new JButton("Highest amount of points");
		JButton distance = new JButton("Shortest distance");
		JButton draw = new JButton("Draw Graph");
		JButton quit = new JButton("Quit");

		draw.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
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

		points.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				String s = (String) JOptionPane.showInputDialog("How many points do you want to get?");

				try {
					model.executeAlgorithmLowestDistance(Integer.parseInt(s));
				} catch (IllegalStateException ex) {
					JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(new JFrame(), "You must enter a integer value", "",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(new JFrame(), "There are no such paths", "",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		distance.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				String s = (String) JOptionPane.showInputDialog("How much distance do you want to walk?");

				try {
					model.executeAlgorithmHighestPoints(Integer.parseInt(s));
				} catch (IllegalStateException ex) {
					JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(new JFrame(), "You must enter a integer value", "",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(new JFrame(), "There are no such paths", "",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		quit.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				System.exit(0);
			}
		});

		int minWidth = Math.max(Math.max(points.getMinimumSize().width, distance.getMinimumSize().width),
				Math.max(draw.getMinimumSize().width, quit.getMinimumSize().width));
		int minHeight = Math.max(Math.max(points.getMinimumSize().height, distance.getMinimumSize().height),
				Math.max(draw.getMinimumSize().height, quit.getMinimumSize().height));

		dimension = new Dimension(minWidth, minHeight);
		points.setMinimumSize(dimension);
		distance.setMinimumSize(dimension);
		draw.setMinimumSize(dimension);
		quit.setMinimumSize(dimension);

		dimension.height *= 1.3;
		points.setPreferredSize(dimension);
		distance.setPreferredSize(dimension);
		draw.setPreferredSize(dimension);
		quit.setPreferredSize(dimension);

		dimension.height *= 1.2;
		points.setMaximumSize(dimension);
		distance.setMaximumSize(dimension);
		draw.setMaximumSize(dimension);
		quit.setMaximumSize(dimension);

		gbc.gridx = 0;
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridy = 0;
		menu.add(draw, gbc);

		gbc.gridy = 1;
		menu.add(distance, gbc);

		gbc.gridy = 2;
		menu.add(points, gbc);

		gbc.gridy = 3;
		menu.add(quit, gbc);

		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;

		JList<String> path = new JList<String>(model.getPath());

		path.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				List<Arc> selectedValues = new ArrayList<Arc>();
				ListModel<String> selected = path.getModel();

				Node last = null;
				Node actual = null;

				for (int i = 0; i < selected.getSize(); i++) {
					last = actual;

					String[] elements = selected.getElementAt(i).split(" ");

					int j = 0;
					actual = null;
					while (actual == null) {
						actual = model.getGraph().getNode(elements[j++]);
					}

					if (last != null)
						selectedValues.add(model.getGraph().getArc(last, actual));
				}

				model.select(selectedValues);
			}
		});

		path.setBackground(new Color(210, 210, 210));
		path.setFont(new Font("Courier", Font.PLAIN, path.getFont().getSize()));
		JScrollPane scrollPane = new JScrollPane(path);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		dimension.width *= 1.5;
		scrollPane.setPreferredSize(dimension);

		menu.add(scrollPane, gbc);

		return menu;
	}

	private JPanel getGraphSection(Modele model) {

		JPanel graph = model.getGraph();
		if (graph == null) {

			graph = new JPanel() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 3683493550838324113L;

				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);

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

	@Override
	public void update(Observable o, Object arg) {
		Modele model = (Modele) o;
		this.drawPrincipalPanel(model);
		this.revalidate();
		this.repaint();
	}

}
