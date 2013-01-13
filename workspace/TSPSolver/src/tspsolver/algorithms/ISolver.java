package tspsolver.algorithms;

public interface ISolver {
	
	/**
	 * 
	 * Each call returns result of next Iteration of the algorithm.
	 * 
	 */
	
	public IterationResult nextIteration();
	
	
	/**
	 * used to set algorithm properties, should be called before calling nextIteration()
	 * @param properties
	 */
}