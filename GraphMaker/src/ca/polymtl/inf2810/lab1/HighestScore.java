package ca.polymtl.inf2810.lab1;

public class HighestScore extends RatioAlgorithm {

	private int maxDistanceTravelled;
	
	public HighestScore(Graph graph, int distanceTravelled) {
		super(graph);
		this.maxDistanceTravelled = distanceTravelled;
	}

	@Override
	public boolean isTooFar(Path potentialPath) {
		return potentialPath.getDistance() > maxDistanceTravelled;
	}

	@Override
	public boolean isFinished(Path potentialPath) {
		return false;
	}

	@Override
	public int compare(Path p1, Path p2) {
		return p2.getTotalPoints() - p1.getTotalPoints();
	}
	
	
}
