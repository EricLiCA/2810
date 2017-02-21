package ca.polymtl.inf2810.lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class RatioAlgorithm {

	private final static int PATH_MEMORY = 500;

	Graph graph;
	Path[] potentialPaths;
	int potentialAmount;
	int firstPotentialIndex;
	List<Path> finishedPaths;

	public RatioAlgorithm(Graph graph) {

		this.firstPotentialIndex = 0;
		this.graph = graph;
		this.potentialPaths = new Path[PATH_MEMORY];
		this.potentialAmount = 0;
		this.finishedPaths = new ArrayList<Path>();

		this.potentialPaths[potentialAmount++] = new Path(graph);
	}

	public Path execute() {
		long startTime = System.currentTimeMillis();
		while (finishedPaths.size() < PATH_MEMORY) {

			Arrays.sort(potentialPaths, firstPotentialIndex, firstPotentialIndex + potentialAmount - 1);

			List<Path> newPaths = this.potentialPaths[firstPotentialIndex++].getSubPaths(this);
			potentialAmount--;

			int copyIndex = PATH_MEMORY;
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

		RatioAlgorithm d = this;

		Collections.sort(finishedPaths, new Comparator<Path>() {

			@Override
			public int compare(Path o1, Path o2) {
				return d.compare(o1, o2);
			}
		});

		System.out.println((System.currentTimeMillis() - startTime) / 1000.0);
		return finishedPaths.get(0);
	}

	public abstract boolean isFinished(Path potentialPath);

	public abstract boolean isTooFar(Path potentialPath);

	public abstract int compare(Path p1, Path p2);

	public void addFinishedPath(Path path) {
		if (!finishedPaths.contains(path))
			finishedPaths.add(path);
	}

}
