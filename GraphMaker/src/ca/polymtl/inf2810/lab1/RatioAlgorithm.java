package ca.polymtl.inf2810.lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The alorithm that finds the most optimized path in a Graph
 * 
 * @author Sébastien Chagnon (1804702)
 *
 */
public abstract class RatioAlgorithm {

	private final static int PATH_MEMORY = 500;

	private Path[] potentialPaths;
	private int potentialAmount;
	private int firstPotentialIndex;
	private List<Path> finishedPaths;

	/**
	 * Constructor
	 * 
	 * @param graph
	 *            the Graph in which execute the algorithm
	 */
	public RatioAlgorithm(Graph graph) {

		this.firstPotentialIndex = 0;

		// Creates an Array of Paths the size of the PATH_MEMORY private final
		// static variable
		this.potentialPaths = new Path[PATH_MEMORY];

		this.potentialAmount = 0;
		this.finishedPaths = new ArrayList<Path>();

		// Adds the first potential Path ; the one that starts at the start
		// point
		this.potentialPaths[potentialAmount++] = new Path(graph);
	}

	public Path execute() {
		// While not enough finished Paths
		while (finishedPaths.size() < PATH_MEMORY) {

			// Sorts the potential Paths by their ratios
			Arrays.sort(potentialPaths, firstPotentialIndex, firstPotentialIndex + potentialAmount - 1);

			// Get all subPaths from the best ratio Path and "removing" it
			List<Path> newPaths = this.potentialPaths[firstPotentialIndex++].getSubPaths(this);
			potentialAmount--;

			// Index of the Path to override if overflow of potential Paths
			int copyIndex = PATH_MEMORY;

			// for each new subPaths, fill the potential Path Array
			// Fills the begining first, then fills empty spaces at the end and
			// finally override potential paths with the worst ratio
			for (Path p : newPaths) {
				if (firstPotentialIndex > 0) {
					potentialPaths[--firstPotentialIndex] = p;
					potentialAmount++;
				} else if (potentialAmount >= PATH_MEMORY) {
					this.potentialPaths[--copyIndex] = p;
				} else {
					this.potentialPaths[potentialAmount++] = p;
				}
			}
		}

		// Used to access this algorithm's instance when defining the Comparator
		// Object
		RatioAlgorithm d = this;

		// Sort finished paths by this algorithm's compare Paths method
		Collections.sort(finishedPaths, new Comparator<Path>() {

			@Override
			public int compare(Path o1, Path o2) {
				return d.compare(o1, o2);
			}
		});

		// Returns the best Path
		return finishedPaths.get(0);
	}

	/**
	 * Returns whether or not the Path is finished
	 * 
	 * @param potentialPath
	 *            the potential Path to check
	 * @return true if it's finished, false otherwise
	 */
	public abstract boolean isFinished(Path potentialPath);

	/**
	 * Returns whether or not the Path is too far
	 * 
	 * @param potentialPath
	 *            the potential Path to check
	 * @return true if it's too far, false otherwise
	 */
	public abstract boolean isTooFar(Path potentialPath);

	/**
	 * Compare two Paths to see which one is the best one
	 * 
	 * @param p1
	 *            first Path to compare
	 * @param p2
	 *            second Path to compare
	 * @return the compare value
	 */
	public abstract int compare(Path p1, Path p2);

	/**
	 * Add a new Path to the finished ones
	 * 
	 * @param path
	 *            the Path to add
	 */
	public void addFinishedPath(Path path) {
		if (!finishedPaths.contains(path))
			finishedPaths.add(path);
	}

}
