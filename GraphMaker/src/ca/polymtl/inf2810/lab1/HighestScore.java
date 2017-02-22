package ca.polymtl.inf2810.lab1;

/**
 * Finds the highest amount of points that can be gained within a set walking
 * distance
 * 
 * @author Sébastien Chagnon (1804702)
 *
 */
public class HighestScore extends RatioAlgorithm {

	private int maxDistanceTravelled;

	/**
	 * Constructor
	 * 
	 * @param graph
	 *            the Graph to use in the algorithm
	 * @param distanceTravelled
	 *            the maximum distance to travel
	 */
	public HighestScore(Graph graph, int distanceTravelled) {
		super(graph);
		this.maxDistanceTravelled = distanceTravelled;
	}

	/**
	 * Returns true if the path length is more than the maximum distance
	 */
	@Override
	public boolean isTooFar(Path potentialPath) {
		return potentialPath.getDistance() > maxDistanceTravelled;
	}

	/**
	 * Always returns false, the highest Score algorithm may theoretically never
	 * end
	 */
	@Override
	public boolean isFinished(Path potentialPath) {
		return false;
	}

	/**
	 * Returns the amount of points difference between two paths
	 */
	@Override
	public int compare(Path p1, Path p2) {
		return p2.getTotalPoints() - p1.getTotalPoints();
	}

}
