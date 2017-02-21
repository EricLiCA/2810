package ca.polymtl.inf2810.lab1;

public class LowestDistance extends RatioAlgorithm {

	int minAmountPoints;
	
	public LowestDistance(Graph graph, int minAmountPoints) {
		super(graph);
		this.minAmountPoints = minAmountPoints;
	}

	@Override
	public boolean isTooFar(Path potentialPath) {
		return false;
	}

	@Override
	public boolean isFinished(Path potentialPath) {
		return potentialPath.getTotalPoints() >= minAmountPoints;
	}

	@Override
	public int compare(Path p1, Path p2) {
		return p1.getDistance() - p2.getDistance();
	}

}
