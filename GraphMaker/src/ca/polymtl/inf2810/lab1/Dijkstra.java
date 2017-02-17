package ca.polymtl.inf2810.lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Dijkstra {

	Graph graph;
	List<Path> potentialPaths;
	List<Path> finishedPaths;

	public Dijkstra (Graph graph) {
		this.graph = graph;
		this.potentialPaths = new ArrayList<Path>();
		this.finishedPaths = new ArrayList<Path>();
		
		this.potentialPaths.add(new Path(graph));
	}
	
	public Path execute() {
		
		while (finishedPaths.size() < 100) {
			this.sort();
			if (potentialPaths.size() > 100)
			this.potentialPaths = this.potentialPaths.subList(0, 100);
			System.out.println(this.potentialPaths.get(0));
			this.potentialPaths.addAll(this.potentialPaths.get(0).getSubPaths(this));
			this.potentialPaths.remove(0);
		}
		
		Collections.sort(finishedPaths, new Comparator<Path>() {

			@Override
			public int compare(Path o1, Path o2) {
				return compare(o1, o2);
			}
		});
		
		
		return finishedPaths.get(0);
	}
	
	public abstract boolean isFinished(Path potentialPath);
	public abstract boolean isTooFar(Path potentialPath);
	public abstract int compare(Path p1, Path p2);
	
	public void addFinishedPath(Path path) {
		if (!finishedPaths.contains(path))
			finishedPaths.add(path);
	}
	
	private void sort() {
		Collections.sort(this.potentialPaths, new Comparator<Path>() {

			@Override
			public int compare(Path o1, Path o2) {
				double r1 = o1.getRatio();
				double r2 = o2.getRatio();

				if (r1 > r2)
					return 1;
				else if (r1 < r2)
					return -1;
				else
					return 0;
			}
		});
	}

}
