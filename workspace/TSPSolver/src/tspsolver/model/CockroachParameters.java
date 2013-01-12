package tspsolver.model;

import tspsolver.annotations.Immutable;


@Immutable
public class CockroachParameters extends AlgorithmParameters{
	
	
	private final int population;
	private final int steps;


	public CockroachParameters(int iterationCount,int population,int steps){
		super(iterationCount);
		this.steps = steps;
		this.population = population;
	}

	public int getSteps() {
		return steps;
	}

	public int getPopulation() {
		return population;
	}
	


}
