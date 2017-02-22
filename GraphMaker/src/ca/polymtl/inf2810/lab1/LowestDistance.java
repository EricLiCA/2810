package ca.polymtl.inf2810.lab1;

/**
 * Finds the lowest distance that can be walked to gain a set amount of points.
 * 
 * @author Sébastien Chagnon (1804702)
 *
 */
public class LowestDistance extends RatioAlgorithm {

	int minAmountPoints;

	/**
	 * Constructor
	 * 
	 * @param graph
	 *            the graph to use
	 * @param minAmountPoints
	 *            the amount of points to gain
	 */
	public LowestDistance(Graph graph, int minAmountPoints) {
		super(graph);
		this.minAmountPoints = minAmountPoints;
	}

	/**
	 * Always returns false, may theoretically be of infinite length
	 */
	@Override
	public boolean isTooFar(Path potentialPath) {
		return false;
	}

	/**
	 * Returns true if the amount of points required has been achieved, false
	 * otherwise
	 */
	@Override
	public boolean isFinished(Path potentialPath) {
		return potentialPath.getTotalPoints() >= minAmountPoints;
	}

	/**
	 * Returns the length difference between two paths
	 */
	@Override
	public int compare(Path p1, Path p2) {
		return p1.getDistance() - p2.getDistance();
	}

}
